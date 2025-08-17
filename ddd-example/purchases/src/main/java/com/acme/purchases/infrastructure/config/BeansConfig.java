package com.acme.purchases.infrastructure.config;

import com.acme.purchases.application.ports.DomainEventPublisher;
import com.acme.purchases.application.ports.IUnitOfWork;
import com.acme.purchases.application.ports.IdempotencyStore;
import com.acme.purchases.application.usecase.CreatePurchaseUseCase;
import com.acme.purchases.domain.repo.PaymentRepository;
import com.acme.purchases.domain.repo.SubscriptionRepository;
import com.acme.purchases.domain.repo.TransactionRepository;
import com.acme.purchases.infrastructure.persistence.uow.SpringUnitOfWork;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BeansConfig {

    @Bean
    public IUnitOfWork unitOfWork(PlatformTransactionManager tm) {
        return new SpringUnitOfWork(tm);
    }

    @Bean
    public CreatePurchaseUseCase useCase(SubscriptionRepository subscriptionRepository,
                                         TransactionRepository transactionRepository,
                                         PaymentRepository paymentRepository,
                                         IUnitOfWork uow,
                                         DomainEventPublisher publisher,
                                         IdempotencyStore idempotencyStore,
                                         ObjectMapper objectMapper) {
        return new CreatePurchaseUseCase(
                subscriptionRepository,
                transactionRepository,
                paymentRepository,
                uow,
                publisher,
                idempotencyStore,
                objectMapper::writeValueAsString
        );
    }
}
