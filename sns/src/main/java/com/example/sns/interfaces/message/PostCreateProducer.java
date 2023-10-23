package com.example.sns.interfaces.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PostCreateProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public PostCreateProducer(final KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void postCreate(String postCreateMessage) {
        log.info("receive from Router send to topic! : " + postCreateMessage);
        kafkaTemplate.send("post_create", postCreateMessage);
    }

    public void postEdit(String postEditMessage) {
        log.info("receive from Router send to topic! : " + postEditMessage);
        kafkaTemplate.send("post_edit", postEditMessage);
    }

    public void postDelete(String postDeleteMessage) {
        log.info("receive from Router send to topic! : " + postDeleteMessage);
        kafkaTemplate.send("post_delete", postDeleteMessage);
    }
}
