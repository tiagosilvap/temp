package com.acme.purchases.application.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public class CreatePurchaseCommand {
    @NotBlank public String subscriptionId;
    @NotBlank public String customerId;
    @NotNull @Size(min=1) public List<Item> items;
    @NotNull public Payment payment;

    public static class Item {
        @NotBlank public String sku;
        @NotBlank public String description;
        @Min(0) public long unitPrice;
        @NotBlank public String currency;
        @Min(1) public int quantity;
    }

    public static class Payment {
        @NotBlank public String method;
        @Min(0) public long amount;
        @NotBlank public String currency;
        @NotBlank public String providerToken;
    }
}
