package com.hotmart.api.subscription.ddd.application.product.create;

import com.hotmart.api.subscription.ddd.domain.product.Product;

public record CreateProductOutput(String id, String name, double price) {

    public static CreateProductOutput from(final String id,
                                           final String name,
                                           final double price) {
        return new CreateProductOutput(id, name, price);
    }

    public static CreateProductOutput from(final Product product) {
        return new CreateProductOutput (
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }
}
