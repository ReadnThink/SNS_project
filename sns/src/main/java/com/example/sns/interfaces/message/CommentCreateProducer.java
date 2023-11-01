package com.example.sns.interfaces.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommentCreateProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public CommentCreateProducer(final KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void commentCreate(String postCreateMessage) {
        log.info("receive from Router send to topic! : " + postCreateMessage);
        kafkaTemplate.send("comment_create", postCreateMessage);
    }

    public void commentEdit(String postEditMessage) {
        log.info("receive from Router send to topic! : " + postEditMessage);
        kafkaTemplate.send("comment_edit", postEditMessage);
    }

    public void commentDelete(String postDeleteMessage) {
        log.info("receive from Router send to topic! : " + postDeleteMessage);
        kafkaTemplate.send("comment_delete", postDeleteMessage);
    }
}
