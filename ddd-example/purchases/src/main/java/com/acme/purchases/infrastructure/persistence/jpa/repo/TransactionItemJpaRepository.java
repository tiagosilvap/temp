package com.acme.purchases.infrastructure.persistence.jpa.repo;

import com.acme.purchases.infrastructure.persistence.jpa.entity.TransactionItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionItemJpaRepository extends JpaRepository<TransactionItemEntity, Long> { }
