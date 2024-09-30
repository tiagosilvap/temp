package com.hotmart.api.subscription.checkouttokens3.service;

import com.hotmart.api.subscription.checkouttokens3.feign.AstroboxCheckoutLoadTokenRequest;
import com.hotmart.api.subscription.checkouttokens3.feign.AstroboxClient;
import com.hotmart.api.subscription.checkouttokens3.feign.AstroboxCheckoutLoadPayloadRequest;
import com.hotmart.api.subscription.checkouttokens3.feign.AstroboxResponse;
import com.hotmart.api.subscription.checkouttokens3.feign.TokenResponse;
import com.hotmart.api.subscription.checkouttokens3.vo.TransactionVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AstroboxService {
    
    @Value("${security.token}")
    public String BEARER_TOKEN;
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
                "Bearer " + BEARER_TOKEN,
                request
        );
    }
    
    public AstroboxResponse getCheckoutLoadExample() {
        var details = new TransactionVO();
        details.setCreationDate(LocalDateTime.of(2024, 7, 10, 0, 0));
        return getCheckoutLoadExample("HP1973886884", details);
    }
    
    public AstroboxResponse getCheckoutLoadExample(String transaction, TransactionVO details) {
        AstroboxCheckoutLoadPayloadRequest.Parameters parameters = new AstroboxCheckoutLoadPayloadRequest.Parameters();
        
        var beginDay = details.getCreationDate().plusDays(1);
        var endDay = details.getCreationDate().minusDays(1);
        
        parameters.setBeginDay(beginDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        parameters.setEndDay(endDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        parameters.setTransactions(List.of(transaction));
        
        AstroboxCheckoutLoadPayloadRequest request = new AstroboxCheckoutLoadPayloadRequest();
        request.setQuery("38c8a557-4fee-4cc4-8b80-5aa7d7261c2f");
        request.setParameters(parameters);
        
        return astroboxClient.getCheckoutLoadExample(
                "api-hotpay-order-checker",
                "Bearer " + BEARER_TOKEN,
                request
        );
    }
}
