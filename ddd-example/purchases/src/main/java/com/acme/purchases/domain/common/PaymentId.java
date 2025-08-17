package com.acme.purchases.domain.common;

import java.util.UUID;

public record PaymentId(String value) {
    public static PaymentId newId() { return new PaymentId("pay_" + UUID.randomUUID()); }
}
