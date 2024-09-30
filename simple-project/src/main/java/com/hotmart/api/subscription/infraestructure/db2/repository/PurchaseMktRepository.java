package com.hotmart.api.subscription.infraestructure.db2.repository;

import com.hotmart.api.subscription.infraestructure.db2.entity.mkt.PurchaseMkt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseMktRepository extends JpaRepository<PurchaseMkt, Long> {
    PurchaseMkt findByTransaction(String transaction);
}

