package com.acme.purchases.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "subscriptions")
public class SubscriptionEntity {
    @Id
    private String id;
    @Column(name = "customer_id", nullable = false)
    private String customerId;
    @Column(nullable = false)
    private String email;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SubscriptionFeatureEntity> features = new ArrayList<>();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public List<SubscriptionFeatureEntity> getFeatures() { return features; }
    public void setFeatures(List<SubscriptionFeatureEntity> features) { this.features = features; }
}
