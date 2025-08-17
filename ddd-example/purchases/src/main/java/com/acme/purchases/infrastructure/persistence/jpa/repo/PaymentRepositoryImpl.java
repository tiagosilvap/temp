package com.acme.purchases.infrastructure.persistence.jpa.repo;

import com.acme.purchases.domain.entities.SubscriptionPayment;
import com.acme.purchases.domain.repo.PaymentRepository;
import com.acme.purchases.infrastructure.persistence.jpa.entity.SubscriptionPaymentEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final SubscriptionPaymentJpaRepository jpa;
    public PaymentRepositoryImpl(SubscriptionPaymentJpaRepository jpa) { this.jpa = jpa; }

    @Override @Transactional
    public void save(SubscriptionPayment p) {
        var e = new SubscriptionPaymentEntity();
        e.setId(p.id().value());
        e.setTransactionId(p.transactionId().value());
        e.setMethod(p.method().name());
        e.setAmount(p.amount().amount());
        e.setCurrency(p.amount().currency().name());
        e.setStatus(p.status().name());
        e.setProviderToken(p.providerToken());
        e.setCreatedAt(p.createdAt());
        jpa.save(e);
    }
}
