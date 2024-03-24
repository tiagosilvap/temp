package com.hotmart.api.subscription.treta.vo;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@Builder
public class SubscriptionResponse {
    
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
    @CsvDate("yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private LocalDateTime creationDate;
    
    @CsvBindByName(column = "dateNextCharge")
    @CsvDate("yyyy-MM-dd'T'HH:mm:ss.SSSX")
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
    
    public static SubscriptionResponse buildResponse(
            SubscriptionData data, OfferValueVO offerValueVO
    ) {
        
        var eligibleSubscriptions =
                data.getCurrentRecurrency() <= data.getEndRecurrency()
                        && !data.getRecurrencyOfferCode().equals("v1yr0c9m");
        
        var currencyCouponOffer = offerValueVO != null
                && offerValueVO.getOfferCurrencyCode() != null
                ? offerValueVO.getOfferCurrencyCode()
                : null;
        
        var hadConversion = currencyCouponOffer != null
                ? !data.getCurrency().equals(currencyCouponOffer)
                : null;
        
        var shouldGenerateSql = eligibleSubscriptions
                && offerValueVO != null
                && offerValueVO.getTotalValue() != null
                && Boolean.FALSE.equals(hadConversion);
        
        SubscriptionResponse response = SubscriptionResponse.builder()
                .subscriptionId(data.getSubscriptionId())
                .subscriptionPaymentId(data.getSubscriptionPaymentId())
                .currencyPurchase(data.getCurrency())
                .currencyCouponOffer(currencyCouponOffer)
                .hadConversion(hadConversion)
                .creationDate(data.getCreationDate())
                .dateNextCharge(data.getMaxDateNextCharge())
                .numberInstallments(data.getNumberInstallments())
                .currentRecurrency(data.getCurrentRecurrency())
                .couponRecurrenceStart(data.getEndRecurrency())
                .eligibleSubscriptions(eligibleSubscriptions)
                .couponOfferCode(data.getRecurrencyOfferCode())
                .transaction(data.getTransaction())
                .build();
        
        if(shouldGenerateSql) {
            response.setRecordedValue(data.getValue());
            response.setCorrectValue(offerValueVO.getTotalValue());
            
            response.setSqlUpdate(generateSqlUpdate(
                    data.getSubscriptionPaymentId(), offerValueVO.getTotalValue())
            );
            
            response.setSqlRollback(generateSqlRollback(
                    data.getSubscriptionPaymentId(), data.getValue())
            );
        }
        return response;
    }
    
    private static String generateSqlUpdate(Long subscriptionPaymentId, BigDecimal value) {
        return "UPDATE hotpay.subscription_payment sp SET sp.value = " + value + " WHERE sp.id = " + subscriptionPaymentId + " limit 1;";
    }
    
    private static String generateSqlRollback(Long subscriptionPaymentId, BigDecimal value) {
        return "--rollback UPDATE hotpay.subscription_payment sp SET sp.value = " + value.setScale(2, BigDecimal.ROUND_HALF_UP) + " WHERE sp.id = " + subscriptionPaymentId + " limit 1;";
    }
}


