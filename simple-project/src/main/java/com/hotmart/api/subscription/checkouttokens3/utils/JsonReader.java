package com.hotmart.api.subscription.checkouttokens3.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotmart.api.subscription.checkouttokens3.vo.TransactionVO;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;

@Component
public class JsonReader {

    public BigDecimal readerToken(String checkoutToken, TransactionVO vo) {
        
        BigDecimal fullAmountValue = null;
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        try {
            
            JsonNode rootNode = objectMapper.readTree(checkoutToken);
            
            if (rootNode.has("value") && rootNode.isObject()) {
                String embeddedJsonString = rootNode.path("value").asText();
                rootNode = objectMapper.readTree(embeddedJsonString);
            }
            
            for (JsonNode productNode : rootNode.path("products")) {
                JsonNode offerNode = productNode.path("offer");
                if(offerNode.path("key").asText().equals(vo.getOfferCode())) {
                    for (JsonNode paymentMethodNode : offerNode.path("paymentMethods")) {
                        if (paymentMethodNode.path("type").asText().equals(vo.getPaymentType())) {
                            if(vo.getInstallments() == 1) {
                                fullAmountValue = BigDecimal.valueOf(paymentMethodNode.path("amount").path("value").asDouble());
                                System.out.println("### Total value: " + fullAmountValue);
                            } else {
                                for (JsonNode installmentNode : paymentMethodNode.path("installments")) {
                                    if (installmentNode.path("number").asInt() == vo.getInstallments()) {
                                        fullAmountValue = BigDecimal.valueOf(installmentNode.path("fullAmount").path("value").asDouble());
                                        System.out.println("### Total value: " + fullAmountValue);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        } catch (IOException exception) {
            throw new RuntimeException(exception.getCause());
        }
        
        return fullAmountValue;
    }
}
