package com.hotmart.api.subscription.treta.rest;

import com.hotmart.api.subscription.treta.service.ProductInstallmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/installment")
public class ProductInstallmentController {
    
    @Autowired
    private ProductInstallmentService service;
    
    @GetMapping("/generateUpdateScripts")
    public ResponseEntity generateUpdateScripts() throws IOException, IllegalAccessException {
        service.generateUpdateScripts();
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/getOffersWithCurrencyConversion")
    public ResponseEntity getOffersWithCurrencyConversion(@RequestBody List<String> offerkeys)
            throws IOException, IllegalAccessException {
        service.getOffersWithCurrencyConversion(offerkeys);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/getTotalSubscriptions")
    public ResponseEntity getTotalSubscriptions() throws IOException, IllegalAccessException {
        service.getTotalSubscriptions();
        return ResponseEntity.ok().build();
    }
}
