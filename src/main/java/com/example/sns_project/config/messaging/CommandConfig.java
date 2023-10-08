package com.example.sns_project.config.messaging;

import com.example.sns_project.domain.messaging.command.Command;
import com.example.sns_project.infra.thread.ThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;

import static com.example.sns_project.domain.messaging.MassagingVO.*;

@Slf4j
@Configuration
@EnableIntegration
public class CommandConfig {
    private final ThreadPool threadPool;

    public CommandConfig(final ThreadPool threadPool) {
        this.threadPool = threadPool;
    }

    @Bean(name = COMMAND_GATEWAY_CHANNEL)
    public ExecutorChannel createChannel() {
        return new ExecutorChannel(threadPool.getDomainEventTaskExecutor());
    }

    @Bean
    public IntegrationFlow createCommandFlow() {
        return IntegrationFlow
                .from(COMMAND_GATEWAY_CHANNEL)
                // endpoint가 2개 이상부터 router가 작동하는것을 확인!
                .route((Command c) -> c.getClass().getSimpleName())
                .get();
    }
}
