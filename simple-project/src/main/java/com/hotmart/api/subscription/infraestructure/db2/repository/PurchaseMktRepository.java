package com.hotmart.api.subscription.infraestructure.db2.repository;

import com.hotmart.api.subscription.infraestructure.db2.entity.OfferMkt;
import com.hotmart.api.subscription.infraestructure.db2.entity.PurchaseMkt;
import com.hotmart.api.subscription.infraestructure.db2.entity.SubscriptionMkt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseMktRepository extends JpaRepository<PurchaseMkt, Long> {
    PurchaseMkt findByTransaction(String transaction);
}

