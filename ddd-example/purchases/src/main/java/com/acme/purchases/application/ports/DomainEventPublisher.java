package com.acme.purchases.application.ports;

import com.acme.purchases.domain.events.DomainEvent;
import java.util.List;

public interface DomainEventPublisher {
    void publishAll(List<DomainEvent> events);
}
