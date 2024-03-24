package com.hotmart.api.subscription.ddd.application.product.delete;

public record DeleteProductCommand(String id) {
    
    public static DeleteProductCommand with(final String id) {
        return new DeleteProductCommand(id);
    }
}
