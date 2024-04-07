package com.hotmart.api.subscription.subscriptionmanager.entity.product;

import java.math.BigDecimal;

public class Offer {
    private Long id;
    private boolean main;
    private BigDecimal price;
    private String currency;
    
    public boolean isMain() {
        return main;
    }
}
