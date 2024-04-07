package com.hotmart.api.subscription.subscriptionmanager.entity.subscription;

import java.math.BigDecimal;

public class Recurrence {
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
