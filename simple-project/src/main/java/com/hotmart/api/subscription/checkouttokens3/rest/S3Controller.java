package com.hotmart.api.subscription.checkouttokens3.rest;

import com.hotmart.api.subscription.checkouttokens3.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/s3")
public class S3Controller {
    
    private final S3Service s3Service;
    
    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }
    
    @GetMapping("/list-files")
    public void listFiles() {
        s3Service.listFilesInBucket("checkout-token-fallback");
    }
    
    @GetMapping("/download-file/{transaction}")
    public ResponseEntity<String> downloadFile(@PathVariable String transaction) {
        return ResponseEntity.ok(s3Service.downloadFile(transaction));
    }
}
