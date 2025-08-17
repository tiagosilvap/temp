package com.acme.purchases.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "transaction_items")
public class TransactionItemEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "transaction_id", nullable=false)
    private TransactionEntity transaction;

    @Column(nullable = false) private String sku;
    @Column(nullable = false) private String description;
    @Column(name="unit_price_amount", nullable = false) private long unitPriceAmount;
    @Column(name="currency", nullable = false) private String currency;
    @Column(nullable = false) private int quantity;
    @Column(name="subtotal_amount", nullable = false) private long subtotalAmount;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TransactionEntity getTransaction() { return transaction; }
    public void setTransaction(TransactionEntity transaction) { this.transaction = transaction; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public long getUnitPriceAmount() { return unitPriceAmount; }
    public void setUnitPriceAmount(long unitPriceAmount) { this.unitPriceAmount = unitPriceAmount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public long getSubtotalAmount() { return subtotalAmount; }
    public void setSubtotalAmount(long subtotalAmount) { this.subtotalAmount = subtotalAmount; }
}
