package com.acme.purchases.infrastructure.http;

import com.acme.purchases.application.dto.CreatePurchaseCommand;
import com.acme.purchases.application.dto.CreatePurchaseResult;
import com.acme.purchases.application.usecase.CreatePurchaseUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@RestController
@RequestMapping("/purchases")
@Tag(name = "Purchases")
public class PurchaseController {

    private final CreatePurchaseUseCase useCase;

    public PurchaseController(CreatePurchaseUseCase useCase) { this.useCase = useCase; }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a purchase (atomic)")
    public ResponseEntity<?> create(@Valid @RequestBody CreatePurchaseCommand body,
                                    @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey,
                                    HttpServletRequest request) throws JsonProcessingException {
        String requestHash = sha256Hex(requestBodyForHash(body));
        try {
            var result = useCase.execute(body, idempotencyKey, requestHash);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (CreatePurchaseUseCase.IdempotentReplayException replay) {
            return ResponseEntity.status(HttpStatus.OK).body(replay.responseBody);
        }
    }

    private String requestBodyForHash(CreatePurchaseCommand body) {
        // A stable hash of significant fields
        StringBuilder sb = new StringBuilder();
        sb.append(body.subscriptionId).append("|").append(body.customerId).append("|").append(body.payment.method)
          .append("|").append(body.payment.amount).append("|").append(body.payment.currency);
        for (var it : body.items) {
            sb.append("|").append(it.sku).append("|").append(it.description).append("|").append(it.unitPrice)
              .append("|").append(it.currency).append("|").append(it.quantity);
        }
        return sb.toString();
    }

    private String sha256Hex(String s) {
        try {
            var md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(s.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) hex.append(String.format("%02x", b));
            return hex.toString();
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}
