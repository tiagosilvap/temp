package com.subscription.domain.exceptions;

import com.subscription.domain.validation.Error;

import java.util.List;

public class DomainException extends RuntimeException {

    private Error error;

    private DomainException(final Error error) {
        super(error.message(), null, true, false);
        this.error = error;
    }

    public static DomainException with(final Error error) {
        return new DomainException(error);
    }

    public Error getError() {
        return error;
    }
}
