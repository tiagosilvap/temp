package com.acme.purchases.infrastructure.persistence.jpa.repo;

import com.acme.purchases.infrastructure.persistence.jpa.entity.SubscriptionPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionPaymentJpaRepository extends JpaRepository<SubscriptionPaymentEntity, String> { }
