package com.hotmart.api.subscription.treta.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OfferValueVO {
    private BigDecimal totalValue;
    private String offerCurrencyCode;
    
    public OfferValueVO(BigDecimal totalValue, String offerCurrencyCode) {
        this.totalValue = totalValue;
        this.offerCurrencyCode = offerCurrencyCode;
    }
}
