package com.hotmart.api.subscription.ddd.infrastructure.product.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {

}

