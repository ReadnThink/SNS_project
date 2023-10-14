package com.example.query.consumer;

import com.example.core.domain.KafkaPostCreate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TestCreateConsumer {
    private int count = 0;
    @KafkaListener(topics = "post_create", groupId = "group_1")
    public void listener(KafkaPostCreate postCreate) {
        count++;
        System.out.println(postCreate + " " + count);
    }
}
