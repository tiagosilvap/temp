package com.hotmart.api.subscription.treta.service;

import com.hotmart.api.subscription.infraestructure.db1.repository.ProductInstallmentRepositoryCustom;
import com.hotmart.api.subscription.treta.filemanager.SubscriptionDataCsvReader;
import com.hotmart.api.subscription.treta.filemanager.CsvWriter;
import com.hotmart.api.subscription.treta.filemanager.FileWriterService;
import com.hotmart.api.subscription.treta.filemanager.SubscriptionResponseV2CsvReader;
import com.hotmart.api.subscription.treta.vo.OfferValueVO;
import com.hotmart.api.subscription.treta.vo.SubscriptionData;
import com.hotmart.api.subscription.treta.vo.SubscriptionResponseV2;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductInstallmentServiceV2 {
    
    public static final String SUBSCRIPTION_DATA = "/Users/tiago.pereira/Desktop/planilha_durval_full.csv";
    public static final String SQL_UPDATES = "/Users/tiago.pereira/Desktop/output/updates/v2/updates.sql";
    public static final String SUBSCRIPTIONS_CHARGED = "/Users/tiago.pereira/Desktop/output/updates/v2/subscriptionsWithNewValueCharged.csv";
    public static final String OFFERS_WITHOUT_INSTALLMENTS = "/Users/tiago.pereira/Desktop/output/updates/v2/offersWithoutInstallmentProduct.csv";
    public static final String OFFERS_WITH_CONVERSION = "/Users/tiago.pereira/Desktop/output/updates/v2/offersWithCurrencyConversion.csv";
    public static final String SUBSCRIPTIONS_IN_FIRST_CHARGE = "/Users/tiago.pereira/Desktop/output/updates/v2/subscriptionsInFirstCharge.csv";
    
    @Autowired
    private ProductInstallmentRepositoryCustom repositoryCustom;
    
    @Autowired
    private SubscriptionResponseV2CsvReader csvReader;
    
    @Autowired
    private FileWriterService fileWriterService;
    
    public void generateUpdateScripts() throws IOException, IllegalAccessException {
        
        var sqlUpates = new ArrayList<String>();
        var sqlRollbacks = new ArrayList<String>();
        var offersWithoutInstallmentProduct = new ArrayList<SubscriptionResponseV2>();
        var offersWithCurrencyConversion = new ArrayList<SubscriptionResponseV2>();
        
        List<SubscriptionResponseV2> subscriptionDataV2 = csvReader.readCsv(SUBSCRIPTION_DATA);
        
        if(CollectionUtils.isNotEmpty(subscriptionDataV2)) {
            
            var subscriptionsInFirstCharge = subscriptionDataV2.stream()
                    .filter(s -> Boolean.TRUE.equals(s.getDelayedInFirst()))
                    .collect(Collectors.toList());
        
            var eligibleSubscriptions = getSubscriptionsWithRecurringLessOrEqualToCouponStart(subscriptionDataV2);
            
            var subscriptionsWithNewValueCharged = getSubscriptionsWithRecurringGreaterThanCouponStart(
                    subscriptionDataV2, eligibleSubscriptions);
            
            if(CollectionUtils.isNotEmpty(eligibleSubscriptions)) {
                
                eligibleSubscriptions.forEach(subscription -> {
                    OfferValueVO correctValue = repositoryCustom.getTotalValue(
                            subscription.getCouponOfferCode(),
                            subscription.getNumberInstallments(),
                            subscription.getCreationDate()
                    );
                    
                    if(correctValue != null
                            && correctValue.getTotalValue() != null
                            && correctValue.getOfferCurrencyCode() != null) {
                        
                        if(!correctValue.getOfferCurrencyCode().equals(subscription.getCurrencyPurchase())) {
                            offersWithCurrencyConversion.add(subscription);
                        } else {
                            generateScripts(subscription, sqlUpates, correctValue, sqlRollbacks);
                        }
                        
                    } else {
                        offersWithoutInstallmentProduct.add(subscription);
                    }
                });
                
                sqlUpates.addAll(sqlRollbacks);
                fileWriterService.writeToConsoleAndFile(sqlUpates, SQL_UPDATES);
                
                var csvWriter = new CsvWriter<SubscriptionResponseV2>();
                csvWriter.writeObjectsToCsv(subscriptionsInFirstCharge, SUBSCRIPTIONS_IN_FIRST_CHARGE);
                csvWriter.writeObjectsToCsv(subscriptionsWithNewValueCharged, SUBSCRIPTIONS_CHARGED);
                csvWriter.writeObjectsToCsv(offersWithoutInstallmentProduct, OFFERS_WITHOUT_INSTALLMENTS);
                csvWriter.writeObjectsToCsv(offersWithCurrencyConversion, OFFERS_WITH_CONVERSION);
            }
        }
    }
    
    private List<SubscriptionResponseV2> getSubscriptionsWithRecurringGreaterThanCouponStart(
            List<SubscriptionResponseV2> subscriptionDataV2,
            List<SubscriptionResponseV2> eligibleSubscriptions) {
        var subscriptionsWithNewValueCharged = new ArrayList<>(subscriptionDataV2);
        subscriptionsWithNewValueCharged.removeAll(eligibleSubscriptions);
        return subscriptionsWithNewValueCharged;
    }
    
    private List<SubscriptionResponseV2> getSubscriptionsWithRecurringLessOrEqualToCouponStart(
            List<SubscriptionResponseV2> subscriptionDataV2) {
        return subscriptionDataV2.stream()
                .filter(s -> s.getCurrentRecurrency() <= s.getCouponRecurrenceStart())
                .filter(s -> !s.getCouponOfferCode().equals("v1yr0c9m"))
                .collect(Collectors.toList());
    }
    
    private void generateScripts(SubscriptionResponseV2 subscription, ArrayList<String> sqlUpates, OfferValueVO correctValue, ArrayList<String> sqlRollbacks) {
        sqlUpates.add("UPDATE hotpay.subscription_payment sp SET sp.value = " + correctValue.getTotalValue() + " WHERE sp.id = " + subscription.getSubscriptionPaymentId() + " limit 1;");
        sqlRollbacks.add("--rollback UPDATE hotpay.subscription_payment sp SET sp.value = " + subscription.getPaymentValue().setScale(2, BigDecimal.ROUND_HALF_UP) + " WHERE sp.id = " + subscription.getSubscriptionPaymentId() + " limit 1;");
    }
}
