package com.subscription.domain.payment;

import com.subscription.domain.Identifier;
import com.subscription.domain.recurringpayment.RecurringPaymentID;
import com.subscription.domain.util.IdUtils;

import java.util.Objects;

public class PaymentID extends Identifier {

    private String value;

    private PaymentID(String value) {
        this.value = value;
    }

    public static PaymentID from(final String id) {
        return new PaymentID(id);
    }

    public static PaymentID unique() {
        return PaymentID.from(IdUtils.uuid());
    }

    @Override
    public String getValue() {
        return value;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PaymentID that = (PaymentID) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

}
