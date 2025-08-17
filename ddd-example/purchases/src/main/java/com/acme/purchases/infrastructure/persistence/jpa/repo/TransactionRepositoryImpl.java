package com.acme.purchases.infrastructure.persistence.jpa.repo;

import com.acme.purchases.domain.common.Currency;
import com.acme.purchases.domain.common.SubscriptionId;
import com.acme.purchases.domain.common.TransactionId;
import com.acme.purchases.domain.entities.Transaction;
import com.acme.purchases.domain.entities.TransactionItem;
import com.acme.purchases.domain.repo.TransactionRepository;
import com.acme.purchases.infrastructure.persistence.jpa.entity.TransactionEntity;
import com.acme.purchases.infrastructure.persistence.jpa.entity.TransactionItemEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionJpaRepository jpa;

    public TransactionRepositoryImpl(TransactionJpaRepository jpa) { this.jpa = jpa; }

    @Override @Transactional
    public void save(Transaction t) {
        var e = new TransactionEntity();
        e.setId(t.id().value());
        e.setSubscriptionId(t.subscriptionId().value());
        e.setStatus(t.status().name());
        e.setCurrency(t.total().currency().name());
        e.setTotalAmount(t.total().amount());
        e.setCreatedAt(t.createdAt());
        for (TransactionItem it : t.items()) {
            var ie = new TransactionItemEntity();
            ie.setTransaction(e);
            ie.setSku(it.sku());
            ie.setDescription(it.description());
            ie.setUnitPriceAmount(it.unitPrice().amount());
            ie.setCurrency(it.unitPrice().currency().name());
            ie.setQuantity(it.quantity().value());
            ie.setSubtotalAmount(it.unitPrice().multiply(it.quantity().value()).amount());
            e.getItems().add(ie);
        }
        jpa.save(e);
    }

    @Override @Transactional(readOnly = true)
    public Optional<Transaction> findById(TransactionId id) {
        return jpa.findById(id.value()).map(e -> {
            var t = new Transaction(new TransactionId(e.getId()), new SubscriptionId(e.getSubscriptionId()));
            for (var it : e.getItems()) {
                t.addItem(it.getSku(), it.getDescription(),
                        new com.acme.purchases.domain.common.Money(it.getUnitPriceAmount(), Currency.valueOf(it.getCurrency())),
                        new com.acme.purchases.domain.common.Quantity(it.getQuantity()));
            }
            t.markCreated();
            return t;
        });
    }
}
