package com.hotmart.api.subscription.ddd.domain.product;

import java.util.UUID;

public class Product {
    private String id;
    private String name;
    private double price;
    
    private Product(final String id,
                   final String name,
                   final double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    
    public static Product newProduct(final String name, final double price) {
        final var id = UUID.randomUUID().toString().toLowerCase().replace("-", "");
        return new Product(id, name, price);
    }
    
    public static Product with(
            final String id,
            final String name,
            final double price) {
        return new Product(id, name, price);
    }
    
    public void update(
            final String name,
            final double price) {
        this.name = name;
        this.price = price;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
}
