package com.example.sns_project.interfaces.gateway;

import com.example.sns_project.domain.messaging.event.Event;
import org.springframework.integration.annotation.MessagingGateway;

import static com.example.sns_project.domain.messaging.MassagingVO.EVENT_GATEWAY_CHANNEL;

@MessagingGateway(defaultRequestChannel = EVENT_GATEWAY_CHANNEL)
public interface EventGateway {
    void request(Event event);
}
