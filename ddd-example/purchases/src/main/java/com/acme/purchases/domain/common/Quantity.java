package com.acme.purchases.domain.common;

public record Quantity(int value) {
    public Quantity {
        if (value < 1) throw new IllegalArgumentException("quantity must be >= 1");
    }
}
