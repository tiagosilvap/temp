package com.acme.purchases.integration;

import com.acme.purchases.application.dto.CreatePurchaseCommand;
import com.acme.purchases.application.usecase.CreatePurchaseUseCase;
import com.acme.purchases.infrastructure.persistence.jpa.repo.SubscriptionJpaRepository;
import com.acme.purchases.infrastructure.persistence.jpa.entity.SubscriptionEntity;
import com.acme.purchases.infrastructure.persistence.jpa.entity.SubscriptionFeatureEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CreatePurchaseUseCaseIntegrationTest {

    @Autowired CreatePurchaseUseCase useCase;
    @Autowired SubscriptionJpaRepository subscriptionJpaRepository;

    @BeforeEach
    void seed() {
        if (!subscriptionJpaRepository.existsById("sub_123")) {
            var s = new SubscriptionEntity();
            s.setId("sub_123"); s.setCustomerId("cus_999"); s.setEmail("test@example.com"); s.setCreatedAt(java.time.Instant.now());
            var f = new SubscriptionFeatureEntity();
            f.setSubscription(s); f.setCode("plan_pro"); f.setEnabled(true);
            s.setFeatures(List.of(f));
            subscriptionJpaRepository.save(s);
        }
    }

    @Test
    @Transactional
    void happyPath() throws JsonProcessingException {
        var cmd = new CreatePurchaseCommand();
        cmd.subscriptionId = "sub_123"; cmd.customerId = "cus_999";
        var item = new CreatePurchaseCommand.Item();
        item.sku = "plan_pro"; item.description = "Plano Pro"; item.unitPrice = 4990; item.currency = "BRL"; item.quantity = 1;
        cmd.items = java.util.List.of(item);
        var p = new CreatePurchaseCommand.Payment();
        p.method = "credit_card"; p.amount = 4990; p.currency = "BRL"; p.providerToken = "tok_abc";
        cmd.payment = p;

        var result = useCase.execute(cmd, "key-1", "hash-1");
        assertThat(result.transactionId).isNotBlank();
        assertThat(result.total.amount).isEqualTo(4990);
        assertThat(result.payment.paymentId).isNotBlank();
    }

    @Test
    void idempotencyConflictOnDifferentPayload() throws JsonProcessingException {
        var cmd = new CreatePurchaseCommand();
        cmd.subscriptionId = "sub_123"; cmd.customerId = "cus_999";
        var item = new CreatePurchaseCommand.Item();
        item.sku = "plan_pro"; item.description = "Plano Pro"; item.unitPrice = 4990; item.currency = "BRL"; item.quantity = 1;
        cmd.items = java.util.List.of(item);
        var p = new CreatePurchaseCommand.Payment();
        p.method = "credit_card"; p.amount = 4990; p.currency = "BRL"; p.providerToken = "tok_abc";
        cmd.payment = p;

        useCase.execute(cmd, "same-key", "hash-A");
        assertThatThrownBy(() -> useCase.execute(cmd, "same-key", "hash-B"))
                .isInstanceOf(CreatePurchaseUseCase.IdempotencyConflictException.class);
    }
}
