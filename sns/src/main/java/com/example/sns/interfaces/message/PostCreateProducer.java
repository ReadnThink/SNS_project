package com.example.sns.interfaces.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PostCreateProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;


    public PostCreateProducer(final KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void postCreate(String postCreateMessage) throws JsonProcessingException {
        System.out.println("send! : " + postCreateMessage);
        kafkaTemplate.send("post_create", postCreateMessage);
    }
}
