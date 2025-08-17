package com.acme.purchases.infrastructure.persistence.jpa.repo;

import com.acme.purchases.infrastructure.persistence.jpa.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionJpaRepository extends JpaRepository<SubscriptionEntity, String> { }
