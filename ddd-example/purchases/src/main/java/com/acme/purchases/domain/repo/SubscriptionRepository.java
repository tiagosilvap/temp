package com.acme.purchases.domain.repo;

import com.acme.purchases.domain.entities.Subscription;
import com.acme.purchases.domain.common.SubscriptionId;

import java.util.Optional;

public interface SubscriptionRepository {
    Optional<Subscription> findById(SubscriptionId id);
    void save(Subscription sub);
}
