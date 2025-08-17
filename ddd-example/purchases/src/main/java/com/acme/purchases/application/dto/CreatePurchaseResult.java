package com.acme.purchases.application.dto;

import java.time.Instant;
import java.util.List;

public class CreatePurchaseResult {
    public String transactionId;
    public String subscriptionId;
    public String status;
    public MoneyDto total;
    public List<ItemDto> items;
    public PaymentDto payment;
    public Instant createdAt;

    public static class MoneyDto {
        public long amount;
        public String currency;
    }
    public static class ItemDto {
        public String sku;
        public String description;
        public long unitPrice;
        public String currency;
        public int quantity;
        public long subtotal;
    }
    public static class PaymentDto {
        public String paymentId;
        public String status;
    }
}
