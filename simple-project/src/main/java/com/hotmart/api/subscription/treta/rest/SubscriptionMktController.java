package com.hotmart.api.subscription.treta.rest;

import com.hotmart.api.subscription.infraestructure.db2.entity.mkt.SubscriptionMkt;
import com.hotmart.api.subscription.treta.service.SubscriptionMktService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/subscription")
public class SubscriptionMktController {
    
    @Autowired
    private SubscriptionMktService service;
    
    @GetMapping("/{subscriptionId}")
    public ResponseEntity<SubscriptionMkt> getTransacaoById(@PathVariable Long subscriptionId) {
        Optional<SubscriptionMkt> subscriptionMkt = service.finSubscriptionById(subscriptionId);
        if (subscriptionMkt.isPresent()) {
            return ResponseEntity.ok(subscriptionMkt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
