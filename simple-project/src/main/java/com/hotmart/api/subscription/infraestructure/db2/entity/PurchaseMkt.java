package com.hotmart.api.subscription.infraestructure.db2.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "compra")
@Data
public class PurchaseMkt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "transacao")
    private String transaction;
    
    @Column(name = "data_pedido")
    private Date releaseDate;
    
    @OneToOne(mappedBy = "purchase", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PurchaseCheckoutToken purchaseCheckoutToken;
}
