package com.acme.purchases.domain.events;

import com.acme.purchases.domain.common.PaymentId;

public record PaymentAuthorized(PaymentId paymentId) implements DomainEvent { }
