package com.hotmart.api.subscription.treta.vo;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ToString
public class SubscriptionData {
    
    @CsvBindByName(column = "value")
    private BigDecimal value;
    
    @CsvBindByName(column = "currency")
    private String currency;
    
    @CsvBindByName(column = "subscription_id")
    private Long subscriptionId;
    
    @CsvBindByName(column = "max_installments")
    private Integer numberInstallments;
    
    @CsvBindByName(column = "max_creation_date")
    @CsvDate("yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private LocalDateTime creationDate;
    
    @CsvBindByName(column = "max_date_next_charge")
    @CsvDate("yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private LocalDateTime maxDateNextCharge;
    
    @CsvBindByName(column = "current_recurrency")
    private Integer currentRecurrency;
    
    @CsvBindByName(column = "end_recurrency")
    private Integer endRecurrency;
    
    @CsvBindByName(column = "subscription_payment_quant")
    private Integer subscriptionPaymentQuant;
    
    @CsvBindByName(column = "subscription_payment_full_quant")
    private Integer subscriptionPaymentFullQuant;
    
    @CsvBindByName(column = "oferta_adesao")
    private String defaultOfferCode;
    
    @CsvBindByName(column = "oferta_cupom")
    private String recurrencyOfferCode;
    
    @CsvBindByName(column = "payment_id")
    private Long subscriptionPaymentId;
    
    @CsvBindByName(column = "hp")
    private String transaction;
    
    @CsvBindByName(column = "pmin")
    private String payloadMin;
    
    @CsvBindByName(column = "pmax")
    private String payloadMax;
}
