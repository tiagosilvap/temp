package com.hotmart.api.subscription.subscriptionmanager.entity.product;

import java.math.BigDecimal;
import java.util.List;

public class Plan {
    private Long id;
    private String periodicity;
    private BigDecimal price;
    private String name;
    private String description;
    private Plan recoveryPlan;
    private List<Offer> offers;
    
    public Offer getMainOffer() {
        return offers.stream().filter(Offer::isMain).findFirst().get();
    }
}
