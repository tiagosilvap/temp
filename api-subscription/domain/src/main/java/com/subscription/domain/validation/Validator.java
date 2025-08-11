package com.subscription.domain.validation;

public abstract class Validator {

    private final ValidationHandler handler;

    public abstract void validate();

    protected Validator(final ValidationHandler handler) {
        this.handler = handler;
    }

    protected ValidationHandler validationHandler() {
        return this.handler;
    }
}
