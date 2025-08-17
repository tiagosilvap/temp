package com.acme.purchases.domain.entities;

import com.acme.purchases.domain.common.*;
import java.time.Instant;
import java.util.*;

public class Subscription {
    private final SubscriptionId id;
    private final CustomerId customerId;
    private final Email email;
    private final Map<String, SubscriptionFeature> features = new HashMap<>();
    private final Instant createdAt;

    public Subscription(SubscriptionId id, CustomerId customerId, Email email, Collection<SubscriptionFeature> features) {
        this.id = Objects.requireNonNull(id);
        this.customerId = Objects.requireNonNull(customerId);
        this.email = Objects.requireNonNull(email);
        this.createdAt = Instant.now();
        if (features != null) {
            for (SubscriptionFeature f : features) this.features.put(f.code(), f);
        }
    }

    public SubscriptionId id() { return id; }
    public CustomerId customerId() { return customerId; }
    public Email email() { return email; }
    public Instant createdAt() { return createdAt; }

    public boolean hasFeature(String code) { return features.containsKey(code); }

    public void enableFeature(String code) {
        features.compute(code, (k, v) -> v == null ? new SubscriptionFeature(k, true, null) : v.enable());
    }

    public void disableFeature(String code) {
        features.computeIfPresent(code, (k, v) -> v.disable());
    }

    public Collection<SubscriptionFeature> features() { return Collections.unmodifiableCollection(features.values()); }
}
