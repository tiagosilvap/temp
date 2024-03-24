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
    
    public static final String BEARER_TOKEN = "Bearer H4sIAAAAAAAAAHVSW5OiOBT%2BRW6BCsqjCJFECCSQcHnpUmklEGy2hVH49RNmqmu3a2qecpKcfLec9xFV58NFhALFbII6FvAB79S47KEJmy7je2T98z6iKV9azyKGZjqisUhBA8VTlCvUlQcmsvgp8oxrp4M1njLaFVkgQvkQ3JVOrHGWaYgnbn9NdBuwBrCYWSEbFVFTRRzYdhrPdQEIp5w1%2FBq7FmMuKDKN50Sr7KQpQabhmDKKOLAcwpE78899pAFh%2FIXlItXbX3mNrgrHpvs%2Fz1V%2FQJjhUG4pvBkTMDrzS8yJxA5rJFG6o0R8f0s1HlKOENGthHLKEpdjygz2S4da1V1I2OtKuE0S5Vt5QYm2Fv4ewUSne8XpZ3o%2BZrqtPGJIOAbfPOhWGkiLEC7%2Fw%2F3KQceMsBIlzWXGS5RuhSHZ79wwVHs%2F0eec%2F5%2Fnd99%2F1S%2B1B2w77aT%2BFrZEx0vQhCl8%2BUnRBNOtx06%2BCoRmqHr0k8sYOEDgdr7jdTBn1OLHKeVDCayumIemZqs8wXWeXNZ%2B0mh5Snp8AHU%2B6lXoIKFwa1zzNp%2BqKpjKKhjVgNXWBm%2B6T9tTs0gf4TTcGdqZo9VtOR%2B6fEOXfiBXENtbr36ch%2BQWe3uZ3sNt8254B81qqx9%2BVJvLkn5sm%2Fi1HfKRHLvPfd0dHVymdu0BvzarTuSXp6QbL748dul0XxzKXPTbdQ4q9pLHqDy6cr%2BAnMRL0b%2BZcNDdtxpJzWs3np%2BFSC5PZj%2FY4y2AUReNh2g9VG6Ff%2BhWwPDOvbpZ4wRDOhGRamKPT%2F1Y3Q7LY%2Bvc%2Bs1TfpQYTiCPLNMTby01n%2FJf5L%2Fw9eMArm2%2F7uzuDrmxrvG7Ba66XGt3eTsfb%2BdF75zPu89N0fT20zgZRuzmiyYoMm%2F74PWqWbWrNTHjqDi%2BFp5hPx3M2HGxsHY%2FAYEfc6LcAwAA";
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
