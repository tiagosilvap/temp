package com.hotmart.api.subscription.ddd.application.product.retrieve.list;

import com.hotmart.api.subscription.ddd.domain.product.Product;

public record RetrieveListProductOutput(String id, String name, double price) {
    public static RetrieveListProductOutput from(final Product product) {
        return new RetrieveListProductOutput(product.getId(), product.getName(), product.getPrice());
    }
}
