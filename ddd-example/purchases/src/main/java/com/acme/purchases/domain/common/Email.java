package com.acme.purchases.domain.common;

import java.util.regex.Pattern;

public record Email(String value) {
    private static final Pattern P = Pattern.compile("^[^@\s]+@[^@\s]+.[^@\s]+$");
    public Email {
        if (value == null || !P.matcher(value).matches()) {
            throw new IllegalArgumentException("invalid email");
        }
    }
}
