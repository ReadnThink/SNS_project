package com.example.query.application.consumer;

import com.example.core.domain.messaging.command.post.kafka.KafkaPostCreate;
import com.example.core.domain.messaging.command.post.kafka.KafkaPostDelete;
import com.example.core.domain.messaging.command.post.kafka.KafkaPostEdit;
import com.example.query.infra.jpa.PostQueryRepositoryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PostConsumer {
    private final ObjectMapper om;
    private final PostQueryRepositoryImpl postQueryRepository;

    public PostConsumer(final PostQueryRepositoryImpl postQueryRepository, final ObjectMapper om) {
        this.postQueryRepository = postQueryRepository;
        this.om = om;
    }

    // todo 중복 줄일 수 있지 않을까?
    @KafkaListener(topics = "post_create", groupId = "group_1")
    public void postCreate(String postCreate) throws JsonProcessingException {
        log.info("KafkaListener post_create-----------------------------------");
        var kafkaPostCreate = om.readValue(postCreate, KafkaPostCreate.class);
        postQueryRepository.save(kafkaPostCreate.toEntity());

        log.info("kafkaPostCreate : " + kafkaPostCreate);
    }

    @KafkaListener(topics = "post_edit", groupId = "group_1")
    public void postEdit(String postEdit) throws JsonProcessingException {
        log.info("KafkaListener post_edit-----------------------------------");
        var kafkaPostEdit = om.readValue(postEdit, KafkaPostEdit.class);
        postQueryRepository.save(kafkaPostEdit.toEntity());

        log.info("kafkaPostEdit : "+kafkaPostEdit);
    }

    @KafkaListener(topics = "post_delete", groupId = "group_1")
    public void postDelete(String postDelete) throws JsonProcessingException {
        log.info("KafkaListener post_delete-----------------------------------");
        var kafkaPostDelete = om.readValue(postDelete, KafkaPostDelete.class);
        log.info("kafkaPostDelete : "+kafkaPostDelete);
    }
}
