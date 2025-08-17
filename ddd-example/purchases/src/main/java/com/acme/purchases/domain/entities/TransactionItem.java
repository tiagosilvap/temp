package com.acme.purchases.domain.entities;

import com.acme.purchases.domain.common.Money;
import com.acme.purchases.domain.common.Quantity;

public record TransactionItem(String sku, String description, Money unitPrice, Quantity quantity) {
    public TransactionItem {
        if (sku == null || sku.isBlank()) throw new IllegalArgumentException("sku required");
        if (unitPrice == null) throw new IllegalArgumentException("unitPrice required");
        if (quantity == null) throw new IllegalArgumentException("quantity required");
    }
}
