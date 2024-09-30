package com.hotmart.api.subscription.checkouttokens3.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.math.BigDecimal;

@FeignClient(name = "hotpayClient", url = "https://api-hotpay.hotmart.com:443/hotpay-api/api", configuration = FeignConfig.class)
public interface HotpayClient {
    
    @PutMapping(
            value = "/v1/subscription/updateValueSubscriptionPayment/{idSubscriptionPayment}/{valueSubscriptionPayment}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void updateValueSubscriptionPayment(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("idSubscriptionPayment") Long idSubscriptionPayment,
            @PathVariable("valueSubscriptionPayment") BigDecimal valueSubscriptionPayment);
}

