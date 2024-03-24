package com.hotmart.api.subscription.treta.service;

import com.hotmart.api.subscription.infraestructure.db2.entity.OfferMkt;
import com.hotmart.api.subscription.infraestructure.db2.repository.InstallmentProductMktRepository;
import com.hotmart.api.subscription.infraestructure.db2.repository.OfferMktRepository;
import com.hotmart.api.subscription.treta.filemanager.SubscriptionDataCsvReader;
import com.hotmart.api.subscription.treta.filemanager.CsvWriter;
import com.hotmart.api.subscription.treta.filemanager.FileWriterService;
import com.hotmart.api.subscription.treta.vo.SubscriptionData;
import com.hotmart.api.subscription.infraestructure.db1.repository.ProductInstallmentRepository;
import com.hotmart.api.subscription.infraestructure.db1.repository.ProductInstallmentRepositoryCustom;
import com.hotmart.api.subscription.treta.vo.OfferValueVO;
import com.hotmart.api.subscription.treta.vo.SubscriptionResponse;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductInstallmentService {
    
    public static final String SUBSCRIPTION_DATA = "/Users/tiago.pereira/Desktop/planilha_durval.csv";
    public static final String TOTAL_SUBSCRIPTIONS = "/Users/tiago.pereira/Desktop/output/totalSubscriptions.csv";
    public static final String SQL_UPDATES = "/Users/tiago.pereira/Desktop/output/updates/updates.sql";
    public static final String SUBSCRIPTIONS_CHARGED = "/Users/tiago.pereira/Desktop/output/updates/subscriptionsWithNewValueCharged.csv";
    public static final String OFFERS_WITHOUT_INSTALLMENTS = "/Users/tiago.pereira/Desktop/output/updates/offersWithoutInstallmentProduct.csv";
    public static final String OFFERS_WITH_CONVERSION = "/Users/tiago.pereira/Desktop/output/updates/offersWithCurrencyConversion.csv";
    public static final String TOTAL_OFFERS_WITH_CONVERSION = "/Users/tiago.pereira/Desktop/output/conversoes/totalOffersWithConversion.csv";
    
    @Autowired
    private ProductInstallmentRepository repository;
    
    @Autowired
    private ProductInstallmentRepositoryCustom repositoryCustom;
    
    @Autowired
    private InstallmentProductMktRepository installmentProductRepository;
    
    @Autowired
    private OfferMktRepository offerRepository;
    
    @Autowired
    private SubscriptionDataCsvReader csvReader;
    
    @Autowired
    private FileWriterService fileWriterService;
    
    public void generateUpdateScripts() throws IOException, IllegalAccessException {
        
        var sqlUpates = new ArrayList<String>();
        var sqlRollbacks = new ArrayList<String>();
        var offersWithoutInstallmentProduct = new ArrayList<SubscriptionData>();
        var offersWithCurrencyConversion = new ArrayList<SubscriptionData>();
        
        List<SubscriptionData> subscriptionData = csvReader.readCsv(SUBSCRIPTION_DATA);
        
        if(CollectionUtils.isNotEmpty(subscriptionData)) {
        
            var eligibleSubscriptions = getSubscriptionsWithRecurringLessOrEqualToCouponStart(subscriptionData);
            
            var subscriptionsWithNewValueCharged = getSubscriptionsWithRecurringGreaterThanCouponStart(
                    subscriptionData, eligibleSubscriptions);
            
            if(CollectionUtils.isNotEmpty(eligibleSubscriptions)) {
                
                eligibleSubscriptions.forEach(subscription -> {
                    OfferValueVO correctValue = repositoryCustom.getTotalValue(
                            subscription.getRecurrencyOfferCode(),
                            subscription.getNumberInstallments(),
                            subscription.getCreationDate()
                    );
                    
                    if(correctValue != null
                            && correctValue.getTotalValue() != null
                            && correctValue.getOfferCurrencyCode() != null) {
                        
                        if(!correctValue.getOfferCurrencyCode().equals(subscription.getCurrency())) {
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
                
                var csvWriter = new CsvWriter<SubscriptionData>();
                csvWriter.writeObjectsToCsv(subscriptionsWithNewValueCharged, SUBSCRIPTIONS_CHARGED);
                csvWriter.writeObjectsToCsv(offersWithoutInstallmentProduct, OFFERS_WITHOUT_INSTALLMENTS);
                csvWriter.writeObjectsToCsv(offersWithCurrencyConversion, OFFERS_WITH_CONVERSION);
            }
        }
    }
    
    public void getTotalSubscriptions() throws IOException, IllegalAccessException {
        var totalSubscriptions = new ArrayList<SubscriptionResponse>();
        List<SubscriptionData> subscriptionData = csvReader.readCsv(SUBSCRIPTION_DATA);
        if(CollectionUtils.isNotEmpty(subscriptionData)) {
            subscriptionData.forEach(data -> {
                OfferValueVO correctValue = repositoryCustom.getTotalValue(
                        data.getRecurrencyOfferCode(),
                        data.getNumberInstallments(),
                        data.getCreationDate());
                
                totalSubscriptions.add(
                        SubscriptionResponse.buildResponse(data, correctValue)
                );
            });
            var csvWriter = new CsvWriter<SubscriptionResponse>();
            csvWriter.writeObjectsToCsv(totalSubscriptions, TOTAL_SUBSCRIPTIONS);
        }
    }
    
    public void getOffersWithCurrencyConversion(List<String> offerkeys) throws IOException, IllegalAccessException {
        var totalOffersWithConversion = new ArrayList<SubscriptionData>();
        List<SubscriptionData> subscriptionData = csvReader.readCsv(SUBSCRIPTION_DATA);
        
        if(CollectionUtils.isNotEmpty(offerkeys)) {
            subscriptionData = subscriptionData.stream()
                    .filter(data -> offerkeys.contains(data.getRecurrencyOfferCode()))
                    .collect(Collectors.toList());
        }
        
        if(CollectionUtils.isNotEmpty(subscriptionData)) {
            subscriptionData.forEach(subscription -> {
                var currency_cdc = repository.findCurrency(
                        subscription.getRecurrencyOfferCode(), subscription.getNumberInstallments()
                );
                
                if(!subscription.getCurrency().equals(currency_cdc)) {
                    totalOffersWithConversion.add(subscription);
                }
            });
        }
        var csvWriter = new CsvWriter<SubscriptionData>();
        csvWriter.writeObjectsToCsv(totalOffersWithConversion, TOTAL_OFFERS_WITH_CONVERSION);
        validateSubscriptionWithConversion(totalOffersWithConversion);
    }
    
    private void validateSubscriptionWithConversion(ArrayList<SubscriptionData> subscriptionWithConversion) throws IOException, IllegalAccessException {
        if(CollectionUtils.isNotEmpty(subscriptionWithConversion)) {
            
            var offersWithoutInstallmentProduct = new ArrayList<SubscriptionData>();
            var others = new ArrayList<SubscriptionData>();
            
            var subscriptionsWithNewValueCharged = subscriptionWithConversion.stream()
                    .filter(s -> s.getCurrentRecurrency() > s.getEndRecurrency())
                    .collect(Collectors.toList());
            
            subscriptionWithConversion.removeAll(subscriptionsWithNewValueCharged);
            
            Map<String, List<SubscriptionData>> subscriptionsByOffer = subscriptionWithConversion.stream()
                    .collect(Collectors.groupingBy(
                            SubscriptionData::getRecurrencyOfferCode,
                            Collectors.toList()
                    ));
            
            subscriptionsByOffer.forEach((offerKey, subscriptions) -> {
                OfferMkt offer = offerRepository.findByKey(offerKey);
                var count = installmentProductRepository.existsByOfferId(offer.getId());
                if(count <= 0) {
                    subscriptions.forEach(s -> offersWithoutInstallmentProduct.add(s));
                } else {
                    subscriptions.forEach(s -> others.add(s));
                }
            });
            
            var csvWriter = new CsvWriter<SubscriptionData>();
            csvWriter.writeObjectsToCsv(subscriptionsWithNewValueCharged, "/Users/tiago.pereira/Desktop/output/conversoes/subscriptionsWithNewValueCharged.csv");
            csvWriter.writeObjectsToCsv(offersWithoutInstallmentProduct, "/Users/tiago.pereira/Desktop/output/conversoes/offersWithoutInstallmentProduct.csv");
            csvWriter.writeObjectsToCsv(others, "/Users/tiago.pereira/Desktop/output/conversoes/others.csv");
        }
    }
    
    private List<SubscriptionData> getSubscriptionsWithRecurringGreaterThanCouponStart(
            List<SubscriptionData> cdcData,
            List<SubscriptionData> eligibleSubscriptions) {
        var subscriptionsWithNewValueCharged = new ArrayList<>(cdcData);
        subscriptionsWithNewValueCharged.removeAll(eligibleSubscriptions);
        return subscriptionsWithNewValueCharged;
    }
    
    private List<SubscriptionData> getSubscriptionsWithRecurringLessOrEqualToCouponStart(
            List<SubscriptionData> subscriptionData) {
        return subscriptionData.stream()
                .filter(s -> s.getCurrentRecurrency() <= s.getEndRecurrency())
                .filter(s -> !s.getRecurrencyOfferCode().equals("v1yr0c9m"))
                .collect(Collectors.toList());
    }
    
    private void generateScripts(SubscriptionData subscription, ArrayList<String> sqlUpates, OfferValueVO correctValue, ArrayList<String> sqlRollbacks) {
        sqlUpates.add("UPDATE hotpay.subscription_payment sp SET sp.value = " + correctValue.getTotalValue() + " WHERE sp.id = " + subscription.getSubscriptionPaymentId() + " limit 1;");
        sqlRollbacks.add("--rollback UPDATE hotpay.subscription_payment sp SET sp.value = " + subscription.getValue().setScale(2, BigDecimal.ROUND_HALF_UP) + " WHERE sp.id = " + subscription.getSubscriptionPaymentId() + " limit 1;");
    }
}
