package com.hotmart.api.subscription.treta.vo;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SubscriptionResponseV2 {
    
    @CsvBindByName(column = "subscriptionId")
    private Long subscriptionId;
    
    @CsvBindByName(column = "subscriptionPaymentId")
    private Long subscriptionPaymentId;
    
    @CsvBindByName(column = "currencyPurchase")
    private String currencyPurchase;
    
    @CsvBindByName(column = "currencyCouponOffer")
    private String currencyCouponOffer;
    
    @CsvBindByName(column = "hadConversion")
    private Boolean hadConversion;
    
    @CsvBindByName(column = "creationDate")
    @CsvDate("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;
    
    @CsvBindByName(column = "dateNextCharge")
    @CsvDate("yyyy-MM-dd HH:mm")
    private LocalDateTime dateNextCharge;
    
    @CsvBindByName(column = "numberInstallments")
    private Integer numberInstallments;
    
    @CsvBindByName(column = "currentRecurrency")
    private Integer currentRecurrency;
    
    @CsvBindByName(column = "couponRecurrenceStart")
    private Integer couponRecurrenceStart;
    
    @CsvBindByName(column = "eligibleSubscriptions")
    private Boolean eligibleSubscriptions;
    
    @CsvBindByName(column = "couponOfferCode")
    private String couponOfferCode;
    
    @CsvBindByName(column = "transaction")
    private String transaction;
    
    @CsvBindByName(column = "recordedValue")
    private BigDecimal recordedValue;
    
    @CsvBindByName(column = "correctValue")
    private BigDecimal correctValue;
    
    @CsvBindByName(column = "sqlUpdate")
    private String sqlUpdate;
    
    @CsvBindByName(column = "sqlRollback")
    private String sqlRollback;
    
//    Novas propriedades adicionadas pelo Durvalito:
    
    @CsvBindByName(column = "firstAfterCouponEnds")
    private Integer firstAfterCouponEnds;
    
    @CsvBindByName(column = "delayedInFirst")
    private Boolean delayedInFirst;

    @CsvBindByName(column = "hotpaySubscriptionId")
    private Long hotpaySubscriptionId;

    @CsvBindByName(column = "id")
    private Long id;

    @CsvBindByName(column = "hasChangePlan")
    private String hasChangePlan;

    @CsvBindByName(column = "subscriptionFrequency")
    private Integer subscriptionFrequency;

    @CsvBindByName(column = "subscriptionStatus")
    private String subscriptionStatus;

    @CsvBindByName(column = "subscriber")
    private Long subscriber;

    @CsvBindByName(column = "sellerId")
    private Long sellerId;

    @CsvBindByName(column = "mustChangeCreditCard")
    private String mustChangeCreditCard;

    @CsvBindByName(column = "maxChargedCycles")
    private Integer maxChargedCycles;

    @CsvBindByName(column = "productId")
    private Long productId;

    @CsvBindByName(column = "subscriptionPlan")
    private Long subscriptionPlan;

    @CsvBindByName(column = "subscriberCode")
    private String subscriberCode;

    @CsvBindByName(column = "currencyCodes")
    private String currencyCodes;

    @CsvBindByName(column = "sameCurrency")
    private Boolean sameCurrency;
    
    @CsvBindByName(column = "paymentValue")
    private BigDecimal paymentValue;
}


