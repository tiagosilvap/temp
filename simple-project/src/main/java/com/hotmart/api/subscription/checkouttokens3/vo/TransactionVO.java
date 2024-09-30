package com.hotmart.api.subscription.checkouttokens3.vo;

import com.hotmart.payment.vo.hotpay.PaymentType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionVO {
    private String offerCode;
    private String paymentType;
    private Integer installments;
    private BigDecimal subscriptionValue;
    private BigDecimal transactionValue;
    private Long paymentId;
    private BigDecimal transactionItemValue;
    
    public TransactionVO(String offerCode,
                         String paymentType,
                         Integer installments,
                         BigDecimal subscriptionValue,
                         BigDecimal transactionValue,
                         Long paymentId,
                         BigDecimal transactionItemValue) {
        this.offerCode = offerCode;
        this.paymentType = paymentType;
        this.installments = installments;
        this.subscriptionValue = subscriptionValue;
        this.transactionValue = transactionValue;
        this.paymentId = paymentId;
        this.transactionItemValue = transactionItemValue;
    }
    
    public String getPaymentType() {
        if(PaymentType.PAYPAL_INTERNACIONAL.name().equals(paymentType)) {
            return PaymentType.PAYPAL.name();
        } else if(PaymentType.WALLET.name().equals(paymentType) && installments !=null && installments > 1) {
            return PaymentType.CREDIT_CARD.name();
        }
        return paymentType;
    }
}
