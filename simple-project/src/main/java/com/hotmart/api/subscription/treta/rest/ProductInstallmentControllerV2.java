package com.hotmart.api.subscription.treta.rest;

import com.hotmart.api.subscription.treta.service.ProductInstallmentService;
import com.hotmart.api.subscription.treta.service.ProductInstallmentServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/installment/v2")
public class ProductInstallmentControllerV2 {
    
    @Autowired
    private ProductInstallmentServiceV2 service;
    
    @GetMapping("/generateUpdateScripts")
    public ResponseEntity generateUpdateScripts() throws IOException, IllegalAccessException {
        service.generateUpdateScripts();
        return ResponseEntity.ok().build();
    }
}
