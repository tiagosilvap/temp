package com.subscription.domain.payment;

import com.subscription.domain.AggregateRoot;

public class Payment extends AggregateRoot<PaymentID> {

    private Payment(PaymentID paymentID) {
        super(paymentID);
    }

    public static Payment newPayment() {
        return new Payment(PaymentID.unique());
    }
}
