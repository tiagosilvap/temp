package com.acme.purchases.domain.events;

import com.acme.purchases.domain.common.Money;
import com.acme.purchases.domain.common.SubscriptionId;
import com.acme.purchases.domain.common.TransactionId;

public record PurchaseCreated(TransactionId transactionId, SubscriptionId subscriptionId, Money total) implements DomainEvent { }
