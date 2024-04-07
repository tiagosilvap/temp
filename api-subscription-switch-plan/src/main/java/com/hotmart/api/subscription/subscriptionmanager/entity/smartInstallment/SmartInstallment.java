package com.hotmart.api.subscription.subscriptionmanager.entity.smartInstallment;

import com.hotmart.api.subscription.subscriptionmanager.entity.subscription.Recurrence;

import java.time.Instant;
import java.util.List;

public class SmartInstallment {
    private Long id;
    private String code;
    private Recurrence adhesionPurchase;
    private Recurrence currentRecurrence;
    private List<Recurrence> recurrences;
    private Long currentOffer;
    private Instant dateNextCharge;
}
