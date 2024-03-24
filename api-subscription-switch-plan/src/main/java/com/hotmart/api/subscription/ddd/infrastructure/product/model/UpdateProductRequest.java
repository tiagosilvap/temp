package com.hotmart.api.subscription.ddd.infrastructure.product.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateProductRequest(
        @JsonProperty("name") String name,
        @JsonProperty("price") double price
) {
}
