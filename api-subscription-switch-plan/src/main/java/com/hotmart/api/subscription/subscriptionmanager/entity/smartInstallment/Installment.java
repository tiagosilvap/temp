package com.hotmart.api.subscription.subscriptionmanager.entity.smartInstallment;

import java.math.BigDecimal;

public class Installment {
    private Long id;
    private Integer number;
    private boolean trialPurchase;
    private String paymentType;
    private BigDecimal value;
    private BigDecimal totalValue;
    private BigDecimal installmentValue;
    private Integer installmentNumber;
    private Long offer;
    
    public boolean isAdhesion() {
        return number != null && number == 0;
    }
}
