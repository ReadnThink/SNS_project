package com.example.sns_project.config.messaging.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;

import static com.example.sns_project.config.messaging.MassagingVO.COMMAND_GATEWAY_CHANNEL;

@Slf4j
@Configuration
@EnableIntegration
public class CommandConfig {
    @Bean(COMMAND_GATEWAY_CHANNEL)
    public MessageChannel createCommandChannel() {
        return new DirectChannel();
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
