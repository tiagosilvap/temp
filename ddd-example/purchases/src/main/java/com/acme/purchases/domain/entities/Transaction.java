package com.acme.purchases.domain.entities;

import com.acme.purchases.domain.common.*;
import com.acme.purchases.domain.events.DomainEvent;
import com.acme.purchases.domain.events.PurchaseCreated;

import java.time.Instant;
import java.util.*;

public class Transaction {
    public enum Status { PENDING_PAYMENT, CONFIRMED, CANCELLED }

    private final TransactionId id;
    private final SubscriptionId subscriptionId;
    private final List<TransactionItem> items = new ArrayList<>();
    private Money total;
    private Status status = Status.PENDING_PAYMENT;
    private final Instant createdAt;
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public Transaction(TransactionId id, SubscriptionId subscriptionId) {
        this.id = Objects.requireNonNull(id);
        this.subscriptionId = Objects.requireNonNull(subscriptionId);
        this.createdAt = Instant.now();
        this.total = null;
    }

    public void addItem(String sku, String description, Money unitPrice, Quantity quantity) {
        if (unitPrice.amount() < 0) throw new IllegalArgumentException("unit price must be >= 0");
        if (quantity.value() < 1) throw new IllegalArgumentException("quantity must be >= 1");
        var item = new TransactionItem(sku, description, unitPrice, quantity);
        if (!items.isEmpty() && !items.get(0).unitPrice().currency().equals(unitPrice.currency())) {
            throw new IllegalArgumentException("currency must be consistent across items");
        }
        items.add(item);
        recalcTotal();
    }

    private void recalcTotal() {
        Money sum = null;
        for (var it : items) {
            var sub = it.unitPrice().multiply(it.quantity().value());
            sum = (sum == null) ? sub : sum.add(sub);
        }
        this.total = sum;
    }

    public void markCreated() {
        if (this.total == null) throw new IllegalStateException("total is null");
        domainEvents.add(new PurchaseCreated(id, subscriptionId, total));
    }

    public TransactionId id() { return id; }
    public SubscriptionId subscriptionId() { return subscriptionId; }
    public List<TransactionItem> items() { return Collections.unmodifiableList(items); }
    public Money total() { return total; }
    public Status status() { return status; }
    public Instant createdAt() { return createdAt; }

    public void confirm() { this.status = Status.CONFIRMED; }
    public void cancel() { this.status = Status.CANCELLED; }

    public List<DomainEvent> pullDomainEvents() {
        var copy = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return copy;
    }
}
