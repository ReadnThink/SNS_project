package com.example.core.config.messaging.gateway;

import com.example.core.domain.messaging.event.Event;
import org.springframework.integration.annotation.MessagingGateway;

import static com.example.core.domain.messaging.MassagingVO.EVENT_GATEWAY_CHANNEL;

@MessagingGateway(defaultRequestChannel = EVENT_GATEWAY_CHANNEL)
public interface EventGateway {
    void request(Event event);
}
