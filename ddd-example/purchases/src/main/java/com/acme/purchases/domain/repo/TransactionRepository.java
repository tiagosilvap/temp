package com.acme.purchases.domain.repo;

import com.acme.purchases.domain.entities.Transaction;
import com.acme.purchases.domain.common.TransactionId;

import java.util.Optional;

public interface TransactionRepository {
    void save(Transaction t);
    Optional<Transaction> findById(TransactionId id);
}
