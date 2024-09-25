package com.hotmart.api.subscription.infraestructure.db2.repository;

import com.hotmart.api.subscription.infraestructure.db2.entity.mkt.SubscriptionMkt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionMktRepository extends JpaRepository<SubscriptionMkt, Long> {
}

