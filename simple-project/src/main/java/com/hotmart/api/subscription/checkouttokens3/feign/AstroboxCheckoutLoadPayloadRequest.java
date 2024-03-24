package com.hotmart.api.subscription.checkouttokens3.feign;

import lombok.Data;

import java.util.List;

@Data
public class AstroboxCheckoutLoadPayloadRequest {
    private String query;
    private Parameters parameters;
    
    @Data
    public static class Parameters {
        private String beginDay;
        private String endDay;
        private List<String> transactions;
    }
}
