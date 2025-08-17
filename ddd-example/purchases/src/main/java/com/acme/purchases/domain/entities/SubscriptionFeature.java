package com.acme.purchases.domain.entities;

import com.acme.purchases.domain.common.Quantity;

public record SubscriptionFeature(String code, boolean enabled, Quantity limit) {
    public SubscriptionFeature {
        if (code == null || code.isBlank()) throw new IllegalArgumentException("feature code is required");
    }
    public SubscriptionFeature enable() { return new SubscriptionFeature(code, true, limit); }
    public SubscriptionFeature disable() { return new SubscriptionFeature(code, false, limit); }
}
