package com.example.core.config.messaging;

import com.example.core.domain.messaging.event.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;

import static com.example.core.domain.messaging.MassagingVO.EVENT_GATEWAY_CHANNEL;

@Slf4j
@Configuration
@EnableIntegration
public class EventConfig {

    @Bean(name = EVENT_GATEWAY_CHANNEL)
    public DirectChannel createDirectChannel() {
        return new DirectChannel();
    }
    @Bean
    public IntegrationFlow createEventFlow() {
        return IntegrationFlow
                .from(EVENT_GATEWAY_CHANNEL)
                .route((Event e) -> e.getClass().getSimpleName())
                .get();
    }
}
