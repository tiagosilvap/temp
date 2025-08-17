package com.acme.purchases.infrastructure.persistence.jpa.repo;

import com.acme.purchases.domain.common.SubscriptionId;
import com.acme.purchases.domain.entities.Subscription;
import com.acme.purchases.domain.repo.SubscriptionRepository;
import com.acme.purchases.infrastructure.persistence.jpa.mapper.SubscriptionMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    private final SubscriptionJpaRepository jpa;

    public SubscriptionRepositoryImpl(SubscriptionJpaRepository jpa) { this.jpa = jpa; }

    @Override @Transactional(readOnly = true)
    public Optional<Subscription> findById(SubscriptionId id) {
        return jpa.findById(id.value()).map(SubscriptionMapper::toDomain);
    }

    @Override @Transactional
    public void save(Subscription sub) {
        jpa.save(SubscriptionMapper.toEntity(sub));
    }
}
