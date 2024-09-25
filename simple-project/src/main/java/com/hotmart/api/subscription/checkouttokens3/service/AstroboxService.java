package com.hotmart.api.subscription.checkouttokens3.service;

import com.hotmart.api.subscription.checkouttokens3.feign.AstroboxCheckoutLoadTokenRequest;
import com.hotmart.api.subscription.checkouttokens3.feign.AstroboxClient;
import com.hotmart.api.subscription.checkouttokens3.feign.AstroboxCheckoutLoadPayloadRequest;
import com.hotmart.api.subscription.checkouttokens3.feign.AstroboxResponse;
import com.hotmart.api.subscription.checkouttokens3.feign.TokenResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AstroboxService {
    
    public static final String BEARER_TOKEN = "Bearer token";
    private final AstroboxClient astroboxClient;
    
    public AstroboxService(AstroboxClient astroboxClient) {
        this.astroboxClient = astroboxClient;
    }
    
    public TokenResponse getCheckoutTokenByTransaction(String transaction) {
        var parameters = new AstroboxCheckoutLoadTokenRequest.Parameters();
        parameters.setTransaction(transaction);
        
        var request = new AstroboxCheckoutLoadTokenRequest();
        request.setQuery("bf3828b9-7dc2-468b-a9a0-aee03920de69");
        request.setParameters(parameters);
        
        return astroboxClient.getCheckoutTokenByTransaction(
                "api-hotpay-order-checker",
                BEARER_TOKEN,
                request
        );
    }
    
    public AstroboxResponse getCheckoutLoadExample() {
        AstroboxCheckoutLoadPayloadRequest.Parameters parameters = new AstroboxCheckoutLoadPayloadRequest.Parameters();
        parameters.setBeginDay("2024-07-10");
        parameters.setEndDay("2024-07-10");
        parameters.setTransactions(List.of("HP1973886884"));
        
        AstroboxCheckoutLoadPayloadRequest request = new AstroboxCheckoutLoadPayloadRequest();
        request.setQuery("38c8a557-4fee-4cc4-8b80-5aa7d7261c2f");
        request.setParameters(parameters);
        
        return astroboxClient.getCheckoutLoadExample(
                "api-hotpay-order-checker",
                BEARER_TOKEN,
                request
        );
    }
}
