package com.example.sns.interfaces.gateway;

import com.example.sns.domain.messaging.event.Event;
import org.springframework.integration.annotation.MessagingGateway;

import static com.example.sns.domain.messaging.MassagingVO.EVENT_GATEWAY_CHANNEL;

@MessagingGateway(defaultRequestChannel = EVENT_GATEWAY_CHANNEL)
public interface EventGateway {
    void request(Event event);
}
