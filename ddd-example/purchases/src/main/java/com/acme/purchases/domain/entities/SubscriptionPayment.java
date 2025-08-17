package com.acme.purchases.domain.entities;

import com.acme.purchases.domain.common.*;
import com.acme.purchases.domain.events.DomainEvent;
import com.acme.purchases.domain.events.PaymentAuthorized;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SubscriptionPayment {
    public enum Status { PENDING, AUTHORIZED, CONFIRMED, FAILED }
    public enum Method { credit_card, pix, boleto }

    private final PaymentId id;
    private final TransactionId transactionId;
    private final Method method;
    private final Money amount;
    private Status status = Status.PENDING;
    private final String providerToken;
    private final Instant createdAt;
    private final List<DomainEvent> events = new ArrayList<>();

    public SubscriptionPayment(PaymentId id, TransactionId transactionId, Method method, Money amount, String providerToken) {
        this.id = Objects.requireNonNull(id);
        this.transactionId = Objects.requireNonNull(transactionId);
        this.method = Objects.requireNonNull(method);
        this.amount = Objects.requireNonNull(amount);
        if (providerToken == null || providerToken.isBlank()) throw new IllegalArgumentException("providerToken required");
        this.providerToken = providerToken;
        this.createdAt = Instant.now();
    }

    public void authorize() {
        this.status = Status.AUTHORIZED;
        events.add(new PaymentAuthorized(id));
    }

    public void confirm() { this.status = Status.CONFIRMED; }
    public void fail() { this.status = Status.FAILED; }

    public PaymentId id() { return id; }
    public TransactionId transactionId() { return transactionId; }
    public Method method() { return method; }
    public Money amount() { return amount; }
    public Status status() { return status; }
    public String providerToken() { return providerToken; }
    public Instant createdAt() { return createdAt; }

    public List<DomainEvent> pullDomainEvents() {
        var copy = new ArrayList<>(events);
        events.clear();
        return copy;
    }
}
