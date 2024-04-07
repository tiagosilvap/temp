package com.hotmart.api.subscription.subscriptionmanager.jpa.subscription;

import com.hotmart.api.subscription.subscriptionmanager.jpa.product.Offer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity(name = "Recurrence")
@Table(name = "recurrence")
public class Recurrence {
    
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "number", nullable = false)
    private Integer number;
    
    @Column(name = "trial_purchase", nullable = false)
    private boolean trialPurchase;
    
    @Column(name = "paymentType", nullable = false)
    private String paymentType;
    
    @Column(name = "value", nullable = false)
    private BigDecimal value;
    
    @Column(name = "totalValue", nullable = false)
    private BigDecimal totalValue;
    
    @Column(name = "installmentValue", nullable = false)
    private BigDecimal installmentValue;
    
    @Column(name = "installmentNumber", nullable = false)
    private Integer installmentNumber;
    
    @JoinColumn(name = "offer")
    @ManyToOne(cascade = CascadeType.ALL)
    private Offer offer;
    
    @JoinColumn(name = "subscription")
    @ManyToOne(cascade = CascadeType.ALL)
    private Subscription subscription;
    
    public boolean isAdhesion() {
        return number != null && number == 0;
    }
}

