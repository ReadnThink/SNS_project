package com.example.sns.interfaces.message;

import com.example.core.domain.KafkaPostCreate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PostCreateProducer {
    private final KafkaTemplate<String, KafkaPostCreate> kafkaTemplate;


    public PostCreateProducer(final KafkaTemplate<String, KafkaPostCreate> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void create(KafkaPostCreate userId) {
        kafkaTemplate.send("post_create", userId);
    }
}
