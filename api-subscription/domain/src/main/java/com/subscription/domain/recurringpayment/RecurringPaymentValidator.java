package com.subscription.domain.recurringpayment;

import com.subscription.domain.validation.Error;
import com.subscription.domain.validation.ValidationHandler;
import com.subscription.domain.validation.Validator;

public class RecurringPaymentValidator extends Validator {

    private final RecurringPayment recurringPayment;

    protected RecurringPaymentValidator(
            final RecurringPayment recurringPayment,
            ValidationHandler handler
    ) {
        super(handler);
        this.recurringPayment = recurringPayment;
    }

    @Override
    public void validate() {
        final var plan = this.recurringPayment.getPlan();
        if (plan == null) {
            this.validationHandler().append(new Error("'plan' should not be null"));
        }
    }
}
