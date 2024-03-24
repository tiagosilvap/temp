package com.hotmart.api.subscription.infraestructure.db1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacoes")
@Data
public class ProductInstallment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_incremental")
    private Long id;
    
    @Column(name = "id", nullable = false)
    private Long productInstallmentId;
    
    @Column(name = "chave", nullable = false)
    private String offerKey;
    
    @Column(name = "oferta_produto_id", nullable = false)
    private Long offerId;
    
    @Column(name = "product_offer_currency_code", nullable = false, length = 10)
    private String offerCurrencyCode;
    
    @Column(name = "hub_transaction_datetime", nullable = false)
    private LocalDateTime datetimeCDC;
    
    @Column(name = "num_parcela", nullable = false)
    private Integer installmentNumber;
    
    @Column(name = "valor_parcela", nullable = false, precision = 10, scale = 2)
    private BigDecimal installmentValue;
    
    @Column(name = "oferta", nullable = false)
    private Long ofertaId;
}
