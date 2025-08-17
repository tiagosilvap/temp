package com.acme.purchases.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "subscription_features")
public class SubscriptionFeatureEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "subscription_id", nullable=false)
    private SubscriptionEntity subscription;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private boolean enabled;

    @Column(name = "limit_value")
    private Integer limitValue;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public SubscriptionEntity getSubscription() { return subscription; }
    public void setSubscription(SubscriptionEntity subscription) { this.subscription = subscription; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public Integer getLimitValue() { return limitValue; }
    public void setLimitValue(Integer limitValue) { this.limitValue = limitValue; }
}
