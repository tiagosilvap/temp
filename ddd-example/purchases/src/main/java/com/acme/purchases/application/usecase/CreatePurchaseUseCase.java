package com.acme.purchases.application.usecase;

import com.acme.purchases.application.dto.CreatePurchaseCommand;
import com.acme.purchases.application.dto.CreatePurchaseResult;
import com.acme.purchases.application.ports.DomainEventPublisher;
import com.acme.purchases.application.ports.IUnitOfWork;
import com.acme.purchases.application.ports.IdempotencyStore;
import com.acme.purchases.domain.common.*;
import com.acme.purchases.domain.entities.*;
import com.acme.purchases.domain.repo.PaymentRepository;
import com.acme.purchases.domain.repo.SubscriptionRepository;
import com.acme.purchases.domain.repo.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;

public class CreatePurchaseUseCase {

    private final SubscriptionRepository subscriptionRepository;
    private final TransactionRepository transactionRepository;
    private final PaymentRepository paymentRepository;
    private final IUnitOfWork uow;
    private final DomainEventPublisher publisher;
    private final IdempotencyStore idempotencyStore;
    private final JsonSerializer json;

    public CreatePurchaseUseCase(SubscriptionRepository subscriptionRepository,
                                 TransactionRepository transactionRepository,
                                 PaymentRepository paymentRepository,
                                 IUnitOfWork uow,
                                 DomainEventPublisher publisher,
                                 IdempotencyStore idempotencyStore,
                                 JsonSerializer json) {
        this.subscriptionRepository = Objects.requireNonNull(subscriptionRepository);
        this.transactionRepository = Objects.requireNonNull(transactionRepository);
        this.paymentRepository = Objects.requireNonNull(paymentRepository);
        this.uow = Objects.requireNonNull(uow);
        this.publisher = Objects.requireNonNull(publisher);
        this.idempotencyStore = Objects.requireNonNull(idempotencyStore);
        this.json = Objects.requireNonNull(json);
    }

    public static class IdempotentReplayException extends RuntimeException {
        public final String responseBody; public final int statusCode;
        public IdempotentReplayException(String body, int status) { this.responseBody = body; this.statusCode = status; }
    }
    public static class IdempotencyConflictException extends RuntimeException {
        public IdempotencyConflictException(String msg){ super(msg); }
    }
    public static class BusinessRuleException extends RuntimeException {
        public BusinessRuleException(String msg){ super(msg); }
    }

    public CreatePurchaseResult execute(CreatePurchaseCommand cmd, String idempotencyKey, String requestHash) throws JsonProcessingException {
        // Idempotency pre-check
        if (idempotencyKey != null && !idempotencyKey.isBlank()) {
            var completed = idempotencyStore.findCompleted(idempotencyKey);
            if (completed.isPresent()) {
                throw new IdempotentReplayException(completed.get().responseBody(), completed.get().statusCode());
            }
            var begin = idempotencyStore.begin(idempotencyKey, requestHash);
            if (begin.conflict()) {
                throw new IdempotencyConflictException("Idempotency-Key already used with different payload");
            }
            if (begin.existed() && begin.stored().isPresent()) {
                var s = begin.stored().get();
                throw new IdempotentReplayException(s.responseBody(), s.statusCode());
            }
        }

        Callable<CreatePurchaseResult> task = () -> {
            // 1) Load and validate subscription
            var subId = new SubscriptionId(cmd.subscriptionId);
            var subOpt = subscriptionRepository.findById(subId);
            if (subOpt.isEmpty()) throw new BusinessRuleException("Subscription not found: " + cmd.subscriptionId);
            var sub = subOpt.get();

            // 2) Build transaction and items
            var txn = new Transaction(TransactionId.newId(), sub.id());
            for (var it : cmd.items) {
                var money = new Money(it.unitPrice, Currency.valueOf(it.currency));
                var qty = new Quantity(it.quantity);
                txn.addItem(it.sku, it.description, money, qty);
            }
            if (txn.items().isEmpty()) throw new BusinessRuleException("Transaction must have items");
            txn.markCreated();

            // 3) Register payment (PENDING)
            var payAmount = new Money(cmd.payment.amount, Currency.valueOf(cmd.payment.currency));
            if (!payAmount.currency().equals(txn.items().get(0).unitPrice().currency())) {
                throw new BusinessRuleException("Payment currency mismatch");
            }
            if (payAmount.amount() != txn.total().amount()) {
                throw new BusinessRuleException("Payment amount must equal transaction total");
            }
            var method = SubscriptionPayment.Method.valueOf(cmd.payment.method);
            var payment = new SubscriptionPayment(PaymentId.newId(), txn.id(), method, payAmount, cmd.payment.providerToken);

            // 4) Persist atomically
            transactionRepository.save(txn);
            paymentRepository.save(payment);

            // 5) Publish domain events (in-memory)
            publisher.publishAll(txn.pullDomainEvents());
            publisher.publishAll(payment.pullDomainEvents());

            // 6) Build result DTO
            return toResult(txn, payment);
        };

        var result = uow.runInTransaction(task);

        // 7) Store idempotency response (within same tx boundary already committed; here we just persist the stored JSON)
        if (idempotencyKey != null && !idempotencyKey.isBlank()) {
            var body = json.toJson(result);
            idempotencyStore.complete(idempotencyKey, requestHash, body, 201);
        }

        return result;
    }

    private CreatePurchaseResult toResult(Transaction txn, SubscriptionPayment payment) {
        var r = new CreatePurchaseResult();
        r.transactionId = txn.id().value();
        r.subscriptionId = txn.subscriptionId().value();
        r.status = txn.status().name();
        var money = new CreatePurchaseResult.MoneyDto();
        money.amount = txn.total().amount();
        money.currency = txn.total().currency().name();
        r.total = money;
        var items = new ArrayList<CreatePurchaseResult.ItemDto>();
        for (var it : txn.items()) {
            var i = new CreatePurchaseResult.ItemDto();
            i.sku = it.sku();
            i.description = it.description();
            i.unitPrice = it.unitPrice().amount();
            i.currency = it.unitPrice().currency().name();
            i.quantity = it.quantity().value();
            i.subtotal = it.unitPrice().multiply(it.quantity().value()).amount();
            items.add(i);
        }
        r.items = items;
        var pd = new CreatePurchaseResult.PaymentDto();
        pd.paymentId = payment.id().value();
        pd.status = payment.status().name();
        r.payment = pd;
        r.createdAt = java.time.Instant.now();
        return r;
    }

    public interface JsonSerializer {
        String toJson(Object o) throws JsonProcessingException;
    }
}
