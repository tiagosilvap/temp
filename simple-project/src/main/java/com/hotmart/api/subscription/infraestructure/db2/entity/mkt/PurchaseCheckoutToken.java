package com.hotmart.api.subscription.infraestructure.db2.entity.mkt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "purchase_checkout_token", schema = "marketplace")
public class PurchaseCheckoutToken {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "purchase")
    private PurchaseMkt purchase;
    
    @Column(name = "checkout4_token")
    private String checkout4Token;
}
