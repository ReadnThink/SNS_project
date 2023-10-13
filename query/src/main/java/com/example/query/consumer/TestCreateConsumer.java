package com.example.query.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TestCreateConsumer {
    private int count = 0;
    @KafkaListener(topics = "coupon_create", groupId = "group_1")
    public void listener(Long userId) {
        count++;
        System.out.println(userId + " " + count);
    }
}
