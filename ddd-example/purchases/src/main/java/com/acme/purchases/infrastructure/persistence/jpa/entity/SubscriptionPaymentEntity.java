package com.acme.purchases.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "subscription_payments")
public class SubscriptionPaymentEntity {
    @Id
    private String id;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private String method;

    @Column(name="amount", nullable = false)
    private long amount;

    @Column(name="currency", nullable = false)
    private String currency;

    @Column(nullable = false)
    private String status;

    @Column(name="provider_token", nullable = false, length = 128)
    private String providerToken;

    @Column(name="created_at", nullable = false)
    private Instant createdAt;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public long getAmount() { return amount; }
    public void setAmount(long amount) { this.amount = amount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getProviderToken() { return providerToken; }
    public void setProviderToken(String providerToken) { this.providerToken = providerToken; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
