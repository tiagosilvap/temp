package com.hotmart.api.subscription.checkouttokens3.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionVO {
    private String offerCode;
    private String paymentType;
    private Integer installments;
    private BigDecimal subscriptionValue;
    private BigDecimal transactionValue;
    
    public TransactionVO(String offerCode,
                         String paymentType,
                         Integer installments,
                         BigDecimal subscriptionValue,
                         BigDecimal transactionValue) {
        this.offerCode = offerCode;
        this.paymentType = paymentType;
        this.installments = installments;
        this.subscriptionValue = subscriptionValue;
        this.transactionValue = transactionValue;
    }
}
