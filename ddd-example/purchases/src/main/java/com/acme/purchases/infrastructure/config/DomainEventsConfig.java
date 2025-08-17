package com.acme.purchases.infrastructure.config;

import com.acme.purchases.application.ports.DomainEventPublisher;
import com.acme.purchases.domain.events.DomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DomainEventsConfig {
    private static final Logger log = LoggerFactory.getLogger(DomainEventsConfig.class);

    @Bean
    public DomainEventPublisher domainEventPublisher() {
        return new DomainEventPublisher() {
            @Override public void publishAll(List<DomainEvent> events) {
                for (var e : events) {
                    log.info("domain_event type={} payload={}", e.getClass().getSimpleName(), e.toString());
                }
            }
        };
    }
}
