package com.subscription.domain.validation.handler;

import com.subscription.domain.exceptions.DomainException;
import com.subscription.domain.validation.Error;
import com.subscription.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {
    @Override
    public ValidationHandler append(final Error error) {
        throw DomainException.with(error);
    }
}
