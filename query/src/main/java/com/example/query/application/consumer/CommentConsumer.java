package com.example.query.application.consumer;

import com.example.core.domain.messaging.command.comment.kafka.KafkaCommentCreate;
import com.example.query.infra.jpa.CommentQueryRepositoryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommentConsumer {
    private final ObjectMapper om;
    private final CommentQueryRepositoryImpl commentQueryRepository;

    public CommentConsumer(final ObjectMapper om, final CommentQueryRepositoryImpl commentQueryRepository) {
        this.om = om;
        this.commentQueryRepository = commentQueryRepository;
    }

    @KafkaListener(topics = "comment_create", groupId = "group_1")
    public void commentCreate(String commentCreate) throws JsonProcessingException {
        log.info("KafkaListener comment_create-----------------------------------");
        var kafkaCommentCreate = om.findAndRegisterModules().readValue(commentCreate, KafkaCommentCreate.class);
        commentQueryRepository.save(kafkaCommentCreate.toEntity());

        log.info("kafkaCommentCreate : " + kafkaCommentCreate);
    }

    @KafkaListener(topics = "comment_edit", groupId = "group_1")
    public void commentEdit(String commentEdit) throws JsonProcessingException {
        log.info("KafkaListener comment_edit-----------------------------------");
        var kafkaCommentEdit = om.findAndRegisterModules().readValue(commentEdit, KafkaCommentCreate.class);
        commentQueryRepository.save(kafkaCommentEdit.toEntity());

        log.info("kafkaCommentEdit : " + kafkaCommentEdit);
    }

    @KafkaListener(topics = "comment_delete", groupId = "group_1")
    public void commentDelete(String commentDelete) throws JsonProcessingException {
        log.info("KafkaListener comment_delete-----------------------------------");
        var kafkaCommentDelete = om.findAndRegisterModules().readValue(commentDelete, KafkaCommentCreate.class);
        commentQueryRepository.delete(kafkaCommentDelete.toEntity());

        log.info("kafkaCommentDelete : " + kafkaCommentDelete);
    }
}
