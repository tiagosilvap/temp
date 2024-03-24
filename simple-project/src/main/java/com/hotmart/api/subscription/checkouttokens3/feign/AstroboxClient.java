package com.hotmart.api.subscription.checkouttokens3.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "astroboxClient", url = "https://api-astrobox.hotmart.com/v1/executor", configuration = FeignConfig.class)
public interface AstroboxClient {
    
    @PostMapping(value = "/reactive/by-id", consumes = MediaType.APPLICATION_JSON_VALUE)
    AstroboxResponse getCheckoutLoadExample(
            @RequestHeader("X-Client-Name") String clientName,
            @RequestHeader("Authorization") String authorization,
            @RequestBody AstroboxCheckoutLoadPayloadRequest request
    );
    
    @PostMapping(value = "/reactive/by-id", consumes = MediaType.APPLICATION_JSON_VALUE)
    TokenResponse getCheckoutTokenByTransaction(
            @RequestHeader("X-Client-Name") String clientName,
            @RequestHeader("Authorization") String authorization,
            @RequestBody AstroboxCheckoutLoadTokenRequest request
    );
}

