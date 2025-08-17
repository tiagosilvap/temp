package com.acme.purchases.application.ports;

import java.util.Optional;

public interface IdempotencyStore {
    Optional<StoredResponse> findCompleted(String key);
    BeginResult begin(String key, String requestHash); // may throw conflict if different hash existing
    void complete(String key, String requestHash, String responseBody, int statusCode);

    record BeginResult(boolean existed, boolean conflict, Optional<StoredResponse> stored) { }
    record StoredResponse(String responseBody, int statusCode) { }
}
