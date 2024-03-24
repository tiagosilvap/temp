package com.hotmart.api.subscription.ddd.domain.product;

import java.util.List;
import java.util.Optional;

public interface ProductGateway {
    Optional<Product> findById(String id);
    
    List<Product> findAll();

    Product save(Product product);
    
    Product update(Product product);

    void deleteById(String id);
}
