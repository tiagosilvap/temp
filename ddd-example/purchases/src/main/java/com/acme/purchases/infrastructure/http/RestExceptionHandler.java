package com.acme.purchases.infrastructure.http;

import com.acme.purchases.application.usecase.CreatePurchaseUseCase;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> err = new HashMap<>();
        err.put("error", "invalid_input");
        err.put("message", ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(CreatePurchaseUseCase.BusinessRuleException.class)
    public ResponseEntity<?> handleBusiness(CreatePurchaseUseCase.BusinessRuleException ex) {
        Map<String, Object> err = new HashMap<>();
        err.put("error", "business_rule");
        err.put("message", ex.getMessage());
        return ResponseEntity.status(422).body(err);
    }

    @ExceptionHandler(CreatePurchaseUseCase.IdempotencyConflictException.class)
    public ResponseEntity<?> handleIdempConflict(CreatePurchaseUseCase.IdempotencyConflictException ex) {
        Map<String, Object> err = new HashMap<>();
        err.put("error", "idempotency_conflict");
        err.put("message", ex.getMessage());
        return ResponseEntity.status(409).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        Map<String, Object> err = new HashMap<>();
        err.put("error", "internal_error");
        err.put("message", "Unexpected error");
        return ResponseEntity.status(500).body(err);
    }
}
