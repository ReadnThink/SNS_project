package com.example.query.config;

import com.example.core.domain.messaging.command.post.KafkaPostCreate;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    /**
     * ConsumerFactory 설정
     */
    @Bean
    public ConsumerFactory<String, KafkaPostCreate> consumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_1");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                new JsonDeserializer<>(KafkaPostCreate.class, new ObjectMapper())
        );
    }

    /**
     * topic으로부터 message를 전달받는 kafka-listener-factory 설정
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaPostCreate> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaPostCreate> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }
}
