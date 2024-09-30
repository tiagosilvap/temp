package com.hotmart.api.subscription.treta.service;

import com.hotmart.api.subscription.infraestructure.db2.entity.mkt.SubscriptionMkt;
import com.hotmart.api.subscription.infraestructure.db2.repository.SubscriptionMktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriptionMktService {
    
    @Autowired
    private SubscriptionMktRepository mktRepository;
    
    public Optional<SubscriptionMkt> finSubscriptionById(Long subscriptionId) {
        return mktRepository.findById(subscriptionId);
    }
}
