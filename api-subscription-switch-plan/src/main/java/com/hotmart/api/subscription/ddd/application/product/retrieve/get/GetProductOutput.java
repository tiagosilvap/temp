package com.hotmart.api.subscription.ddd.application.product.retrieve.get;

import com.hotmart.api.subscription.ddd.domain.product.Product;

public record GetProductOutput(String name, double price) {
    public static GetProductOutput from(final Product product) {
        return new GetProductOutput(product.getName(), product.getPrice());
    }
}
