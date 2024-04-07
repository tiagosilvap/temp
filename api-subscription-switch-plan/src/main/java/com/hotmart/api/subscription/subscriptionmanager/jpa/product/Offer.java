package com.hotmart.api.subscription.subscriptionmanager.jpa.product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity(name = "Offer")
@Table(name = "offer")
public class Offer {
    
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    
    private boolean main;
    
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    
    @Column(name = "currency", nullable = false)
    private String currency;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "plan")
    private Plan plan;
    
    @JoinColumn(name = "product")
    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;
}
