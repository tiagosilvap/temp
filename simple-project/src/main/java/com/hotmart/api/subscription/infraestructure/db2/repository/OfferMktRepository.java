package com.hotmart.api.subscription.infraestructure.db2.repository;

import com.hotmart.api.subscription.infraestructure.db2.entity.mkt.OfferMkt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferMktRepository extends JpaRepository<OfferMkt, Long> {
    OfferMkt findByKey(String offerKey);
}

