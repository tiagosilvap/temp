package com.hotmart.api.subscription.checkouttokens3.feign;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionResponse {
    private String transaction;
    private String checkoutLoad;
}
