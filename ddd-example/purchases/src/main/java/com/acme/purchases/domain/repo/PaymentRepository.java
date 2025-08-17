package com.acme.purchases.domain.repo;

import com.acme.purchases.domain.entities.SubscriptionPayment;

public interface PaymentRepository {
    void save(SubscriptionPayment p);
}
