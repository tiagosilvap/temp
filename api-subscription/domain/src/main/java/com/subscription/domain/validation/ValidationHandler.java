package com.subscription.domain.validation;

import java.util.List;

public interface ValidationHandler {
    ValidationHandler append(Error error);
}
