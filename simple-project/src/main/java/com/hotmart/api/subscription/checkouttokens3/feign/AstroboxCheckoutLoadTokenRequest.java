package com.hotmart.api.subscription.checkouttokens3.feign;

import lombok.Data;

import java.util.List;

@Data
public class AstroboxCheckoutLoadTokenRequest {
    private String query;
    private Parameters parameters;
    
    @Data
    public static class Parameters {
        private String transaction;
    }
}
