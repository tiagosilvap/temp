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
    
    public TransactionVO(String offerCode,
                         String paymentType,
                         Integer installments,
                         BigDecimal subscriptionValue,
                         BigDecimal transactionValue,
                         Long paymentId) {
        this.offerCode = offerCode;
        this.paymentType = paymentType;
        this.installments = installments;
        this.subscriptionValue = subscriptionValue;
        this.transactionValue = transactionValue;
        this.paymentId = paymentId;
    }
    
    public String getPaymentType() {
        if(PaymentType.PAYPAL_INTERNACIONAL.getCode().equals(paymentType)) {
            return PaymentType.PAYPAL.getCode();
        } else if(PaymentType.WALLET.getCode().equals(paymentType) && installments !=null && installments > 1) {
            return PaymentType.CREDIT_CARD.getCode();
        }
        return paymentType;
    }
}
