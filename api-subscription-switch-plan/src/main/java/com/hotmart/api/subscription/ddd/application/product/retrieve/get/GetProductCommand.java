package com.hotmart.api.subscription.ddd.application.product.retrieve.get;

public record GetProductCommand(String id) {
    public static GetProductCommand from(final String id) {
        return new GetProductCommand(id);
    }
}
