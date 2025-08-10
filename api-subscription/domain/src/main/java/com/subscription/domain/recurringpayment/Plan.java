package com.subscription.domain.recurringpayment;

import com.subscription.domain.ValueObject;
import com.subscription.domain.util.IdUtils;

import java.util.Objects;

public class Plan extends ValueObject {
    private final String id;

    private Plan(String id) {
        this.id = Objects.requireNonNull(id);
    }

    public String getId() {
        return id;
    }

    public static Plan newPlan() {
        return new Plan(IdUtils.uuid());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Plan plan = (Plan) o;
        return Objects.equals(id, plan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
