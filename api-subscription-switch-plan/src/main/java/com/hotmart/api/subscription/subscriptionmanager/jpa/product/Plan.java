package com.hotmart.api.subscription.subscriptionmanager.jpa.product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.List;

@Entity(name = "Plan")
@Table(name = "plan")
public class Plan {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "periodicity", nullable = false)
    private String periodicity;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "description", nullable = false)
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "recovery_plan")
    private Plan recoveryPlan;
    
    @OneToMany(mappedBy = "plan", fetch = FetchType.LAZY)
    private List<Offer> offers;
}
