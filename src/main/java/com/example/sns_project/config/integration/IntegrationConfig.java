package com.example.sns_project.config.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@EnableIntegration
public class IntegrationConfig {
    @Bean
    public MessageChannel createPostChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow createPostFlow() {
        return IntegrationFlow
                .from("createPostChannel")
                .handle("postProcessingService", "processCreatePostRequest")
                .get();
    }
}
