package com.acme.purchases.infrastructure.idempotency;

import com.acme.purchases.application.ports.IdempotencyStore;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Component
public class JpaIdempotencyStore implements IdempotencyStore {

    private final IdempotencyKeyJpaRepository jpa;

    public JpaIdempotencyStore(IdempotencyKeyJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StoredResponse> findCompleted(String key) {
        return jpa.findByIdempotencyKey(key).filter(e -> "COMPLETED".equals(e.getStatus()))
                .map(e -> new StoredResponse(e.getResponsePayload(), e.getResponseStatus() == null ? 200 : e.getResponseStatus()));
    }

    @Override
    @Transactional
    public BeginResult begin(String key, String requestHash) {
        var existing = jpa.findByIdempotencyKey(key);
        if (existing.isPresent()) {
            var e = existing.get();
            if (!e.getRequestHash().equals(requestHash)) {
                return new BeginResult(true, true, Optional.empty());
            }
            if ("COMPLETED".equals(e.getStatus())) {
                return new BeginResult(true, false, Optional.of(new StoredResponse(e.getResponsePayload(), e.getResponseStatus() == null ? 200 : e.getResponseStatus())));
            }
            // IN_PROGRESS same payload
            return new BeginResult(true, false, Optional.empty());
        }
        var e = new IdempotencyKeyEntity();
        e.setIdempotencyKey(key);
        e.setRequestHash(requestHash);
        e.setStatus("IN_PROGRESS");
        e.setCreatedAt(Instant.now());
        e.setUpdatedAt(Instant.now());
        jpa.save(e);
        return new BeginResult(false, false, Optional.empty());
    }

    @Override
    @Transactional
    public void complete(String key, String requestHash, String responseBody, int statusCode) {
        var e = jpa.findByIdempotencyKey(key).orElseThrow();
        e.setStatus("COMPLETED");
        e.setResponsePayload(responseBody);
        e.setResponseStatus(statusCode);
        e.setUpdatedAt(Instant.now());
        jpa.save(e);
    }
}
