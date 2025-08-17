package com.acme.purchases.infrastructure.persistence.jpa.repo;

import com.acme.purchases.infrastructure.persistence.jpa.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, String> { }
