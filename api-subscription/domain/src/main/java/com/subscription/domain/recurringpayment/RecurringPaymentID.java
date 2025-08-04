package com.subscription.domain.recurringpayment;

import com.subscription.domain.Identifier;
import com.subscription.domain.ValueObject;
import com.subscription.domain.util.IdUtils;

import java.util.Objects;

public class RecurringPaymentID extends Identifier {

    private final String value;

    private RecurringPaymentID(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static RecurringPaymentID from(final String id) {
        return new RecurringPaymentID(id);
    }

    public static RecurringPaymentID unique() {
        return RecurringPaymentID.from(IdUtils.uuid());
    }

    @Override
    public String getValue() {
        return value;
    }
}
