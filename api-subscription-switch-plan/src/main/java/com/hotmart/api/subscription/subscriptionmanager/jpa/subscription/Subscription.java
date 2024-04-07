package com.hotmart.api.subscription.subscriptionmanager.jpa.subscription;

import com.hotmart.api.subscription.subscriptionmanager.jpa.product.Offer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.List;

@Entity(name = "Subscription")
@Table(name = "subscription")
public class Subscription {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "code", nullable = false)
    private String code;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "adhesion_purchase")
    private Recurrence adhesionPurchase;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "current_recurrence")
    private Recurrence currentRecurrence;
    
    @OneToMany(mappedBy = "subscription", fetch = FetchType.LAZY)
    private List<Recurrence> recurrences;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "current_offer")
    private Offer currentOffer;
    
    @Column(name = "due_date")
    private Integer dueDate;
    
    @Column(name = "dateNextCharge")
    private Instant dateNextCharge;
    
}

