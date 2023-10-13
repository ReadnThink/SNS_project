package com.example.sns.config.messaging;

import com.example.sns.domain.messaging.event.Event;
import com.example.sns.infra.thread.ThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;

import static com.example.sns.domain.messaging.MassagingVO.EVENT_GATEWAY_CHANNEL;

@Slf4j
@Configuration
@EnableIntegration
public class EventConfig {
    private final ThreadPool threadPool;

    public EventConfig(final ThreadPool threadPool) {
        this.threadPool = threadPool;
    }

    @Bean(name = EVENT_GATEWAY_CHANNEL)
    public ExecutorChannel createChannel() {
        return new ExecutorChannel(threadPool.getDomainEventTaskExecutor());
    }

    @Bean
    public IntegrationFlow createEventFlow() {
        return IntegrationFlow
                .from(EVENT_GATEWAY_CHANNEL)
                .route((Event e) -> e.getClass().getSimpleName())
                .get();
    }
}
