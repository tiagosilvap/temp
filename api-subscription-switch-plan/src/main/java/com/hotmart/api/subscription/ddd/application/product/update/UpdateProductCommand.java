package com.hotmart.api.subscription.ddd.application.product.update;

public record UpdateProductCommand(String id, String name, double price) {
    
    public static UpdateProductCommand with(
            final String id,
            final String name,
            final double price
    ) {
        return new UpdateProductCommand(id, name, price);
    }
}
