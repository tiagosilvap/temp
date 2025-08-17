package com.acme.purchases.domain.common;

import java.util.UUID;

public record TransactionId(String value) {
    public static TransactionId newId() { return new TransactionId("txn_" + UUID.randomUUID()); }
}
