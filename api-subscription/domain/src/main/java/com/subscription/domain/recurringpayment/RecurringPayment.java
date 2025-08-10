package com.subscription.domain.recurringpayment;

import com.subscription.domain.AggregateRoot;
import com.subscription.domain.payment.Payment;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class RecurringPayment extends AggregateRoot<RecurringPaymentID> {

    private Integer currentRecurrence;
    private Integer maxChargeCycle;
    private LocalDateTime createAt;
    private RecurringPaymentStatus status;
    private Integer interval;
    private IntervalType intervalType;
    private LocalDateTime dateNextCharge;
    private Payment firstPayment;
    private RecurringPaymentType type;
    private Plan plan;

    private RecurringPayment(
            final RecurringPaymentID recurringPaymentID,
            final Integer maxChargeCycle,
            Integer interval,
            IntervalType intervalType,
            Payment firstPayment,
            RecurringPaymentType type,
            Plan plan) {

        super(recurringPaymentID);
        this.currentRecurrence = 1;
        this.maxChargeCycle = maxChargeCycle;
        this.createAt = LocalDateTime.now();
        this.status = RecurringPaymentStatus.INITIAL;
        this.interval = interval;
        this.intervalType = intervalType;
        this.firstPayment = firstPayment;
        this.dateNextCharge = defineNextCharge(this.createAt, this.interval, this.intervalType);
        this.type = type;
        this.plan = plan;
    }

    public void updateDateNextCharge(LocalDateTime newDateNextCharge) {
        this.dateNextCharge = Objects.requireNonNull(newDateNextCharge);
    }

    private LocalDateTime defineNextCharge(
            LocalDateTime createAt,
            Integer interval,
            IntervalType intervalType
    ) {
        switch (intervalType) {
            case MONTH:
                return createAt.plusMonths(interval).with(LocalTime.NOON);
            case WEEK:
                return createAt.plusWeeks(interval).with(LocalTime.NOON);
            default:
                throw new IllegalArgumentException("Interval type is not supported: " + intervalType);
        }
    }

    public static RecurringPayment newRecurringPayment(
            Integer maxChargeCycle,
            Integer interval,
            IntervalType intervalType,
            Payment firstPayment,
            RecurringPaymentType type,
            Plan plan
    ) {
        return new RecurringPayment(
                RecurringPaymentID.unique(),
                maxChargeCycle,
                interval,
                intervalType,
                firstPayment,
                type,
                plan
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

    public Integer getInterval() {
        return interval;
    }

    public IntervalType getIntervalType() {
        return intervalType;
    }

    public LocalDateTime getDateNextCharge() {
        return dateNextCharge;
    }

    public Payment getFirstPayment() {
        return firstPayment;
    }

    public RecurringPaymentType getType() {
        return type;
    }

    public Plan getPlan() {
        return plan;
    }
}
