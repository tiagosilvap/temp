package com.hotmart.api.subscription.ddd.infrastructure.product.persistence;

import com.hotmart.api.subscription.ddd.domain.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProductEntity {
    
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    private String name;
    private double price;
    
    public ProductEntity() {}
    
    private ProductEntity(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    
    public Product toAggregate() {
        return Product.with(getId(), getName(), getPrice());
    }
    
    public static ProductEntity from(final Product product) {
        return new ProductEntity(product.getId(), product.getName(), product.getPrice());
    }
    
}
