package com.hotmart.api.subscription.infraestructure.db2.repository.hp;

import com.hotmart.api.subscription.infraestructure.db2.entity.hp.Transaction;
import com.hotmart.api.subscription.infraestructure.db2.entity.mkt.OfferMkt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findByHotpayReference(String hotpayReference);
}

