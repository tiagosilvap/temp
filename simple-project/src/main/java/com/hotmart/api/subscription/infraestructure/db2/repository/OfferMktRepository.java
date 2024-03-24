package com.hotmart.api.subscription.infraestructure.db2.repository;

import com.hotmart.api.subscription.infraestructure.db2.entity.OfferMkt;
import com.hotmart.api.subscription.infraestructure.db2.entity.SubscriptionMkt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferMktRepository extends JpaRepository<OfferMkt, Long> {
    OfferMkt findByKey(String offerKey);
}

