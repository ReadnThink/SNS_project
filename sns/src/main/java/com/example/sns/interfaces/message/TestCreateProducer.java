package com.example.sns.interfaces.message;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TestCreateProducer {

    private final KafkaTemplate<String, Long> kafkaTemplate;


    public TestCreateProducer(final KafkaTemplate<String, Long> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void create(Long userId) {
        kafkaTemplate.send("coupon_create", userId);
    }
}
