package com.hotmart.api.subscription.ddd.application.product.update;

import com.hotmart.api.subscription.ddd.domain.product.Product;

public record UpdateProductOutput(String name, double price) {
    public static UpdateProductOutput from(final Product product) {
        return new UpdateProductOutput(product.getName(), product.getPrice());
    }
}
