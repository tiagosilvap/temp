package com.subscription.domain.recurringpayment;

import com.subscription.domain.AggregateRoot;

import java.time.LocalDateTime;

public class RecurringPayment extends AggregateRoot<RecurringPaymentID> {

    private Integer currentRecurrence;
    private Integer maxChargeCycle;
    private LocalDateTime createAt;
    private RecurringPaymentStatus status;

    private RecurringPayment(
            final RecurringPaymentID recurringPaymentID,
            final Integer maxChargeCycle
    ) {
        super(recurringPaymentID);
        this.currentRecurrence = 1;
        this.maxChargeCycle = maxChargeCycle;
        this.createAt = LocalDateTime.now();
        this.status = RecurringPaymentStatus.INITIAL;
    }

    public static RecurringPayment newRecurringPayment(
            Integer maxChargeCycle
    ) {
        return new RecurringPayment(
                RecurringPaymentID.unique(),
                maxChargeCycle
        );
    }

    public Integer getCurrentRecurrence() {
        return currentRecurrence;
    }

    public Integer getMaxChargeCycle() {
        return maxChargeCycle;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public RecurringPaymentStatus getStatus() {
        return status;
    }
}
