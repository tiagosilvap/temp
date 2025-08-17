package com.acme.purchases.domain.common;

import java.util.UUID;

public record SubscriptionId(String value) {
    public static SubscriptionId newId() { return new SubscriptionId("sub_" + UUID.randomUUID()); }
}
