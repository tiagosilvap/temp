package com.acme.purchases.infrastructure.persistence.jpa.mapper;

import com.acme.purchases.domain.common.CustomerId;
import com.acme.purchases.domain.common.Email;
import com.acme.purchases.domain.common.SubscriptionId;
import com.acme.purchases.domain.entities.Subscription;
import com.acme.purchases.domain.entities.SubscriptionFeature;
import com.acme.purchases.infrastructure.persistence.jpa.entity.SubscriptionEntity;
import com.acme.purchases.infrastructure.persistence.jpa.entity.SubscriptionFeatureEntity;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionMapper {
    public static Subscription toDomain(SubscriptionEntity e) {
        var features = new ArrayList<SubscriptionFeature>();
        if (e.getFeatures() != null) {
            for (var f : e.getFeatures()) {
                features.add(new SubscriptionFeature(f.getCode(), f.isEnabled(), f.getLimitValue() == null ? null : new com.acme.purchases.domain.common.Quantity(f.getLimitValue())));
            }
        }
        return new Subscription(new SubscriptionId(e.getId()), new CustomerId(e.getCustomerId()), new Email(e.getEmail()), features);
    }

    public static SubscriptionEntity toEntity(Subscription s) {
        var e = new SubscriptionEntity();
        e.setId(s.id().value());
        e.setCustomerId(s.customerId().value());
        e.setEmail(s.email().value());
        e.setCreatedAt(s.createdAt());
        List<SubscriptionFeatureEntity> features = new ArrayList<>();
        for (var f : s.features()) {
            var fe = new SubscriptionFeatureEntity();
            fe.setSubscription(e);
            fe.setCode(f.code());
            fe.setEnabled(f.enabled());
            fe.setLimitValue(f.limit() == null ? null : f.limit().value());
            features.add(fe);
        }
        e.setFeatures(features);
        return e;
    }
}
