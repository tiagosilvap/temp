package com.hotmart.api.subscription.ddd.infrastructure.product;

import com.hotmart.api.subscription.ddd.domain.product.Product;
import com.hotmart.api.subscription.ddd.domain.product.ProductGateway;
import com.hotmart.api.subscription.ddd.infrastructure.product.persistence.ProductEntity;
import com.hotmart.api.subscription.ddd.infrastructure.product.persistence.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductMySQLGateway implements ProductGateway {

    private final ProductRepository repository;

    public ProductMySQLGateway(final ProductRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Optional<Product> findById(String id) {
        return repository.findById(id).map(ProductEntity::toAggregate);
    }
    
    @Override
    public List<Product> findAll() {
        return repository.findAll().stream()
                .map(ProductEntity::toAggregate)
                .collect(Collectors.toList());
    }
    
    @Override
    public Product save(Product product) {
        return persist(product);
    }
    
    @Override
    public Product update(Product product) {
        return persist(product);
    }
    
    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
    
    private Product persist(final Product product) {
        return this.repository.save(ProductEntity.from(product)).toAggregate();
    }
}
