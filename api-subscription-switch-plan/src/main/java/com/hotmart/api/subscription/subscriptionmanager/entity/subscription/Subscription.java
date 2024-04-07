package com.hotmart.api.subscription.subscriptionmanager.entity.subscription;

import java.time.Instant;
import java.util.List;

public class Subscription {
    private Long id;
    private String code;
    private Recurrence adhesionPurchase;
    private Recurrence currentRecurrence;
    private List<Recurrence> recurrences;
    private Long currentPlan;
    private Long currentOffer;
    private Integer dueDate;
    private Instant dateNextCharge;
    
}
