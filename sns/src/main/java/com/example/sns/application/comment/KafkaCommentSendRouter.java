package com.example.sns.application.comment;

import com.example.core.domain.messaging.command.comment.kafka.KafkaCommentCreate;
import com.example.sns.interfaces.message.CommentCreateProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import static com.example.core.domain.messaging.MassagingVO.EVENT_GATEWAY_COMMENT_CREATE_CHANNEL;

@Slf4j
@Service
public class KafkaCommentSendRouter {
    private final CommentCreateProducer commentCreateProducer;
    private final ObjectMapper om;

    public KafkaCommentSendRouter(final CommentCreateProducer commentCreateProducer, final ObjectMapper om) {
        this.commentCreateProducer = commentCreateProducer;
        this.om = om;
    }

    @ServiceActivator(inputChannel = EVENT_GATEWAY_COMMENT_CREATE_CHANNEL)
    public void send(KafkaCommentCreate commentCreate) throws JsonProcessingException {
        log.info("Router : kafkaCommentCreate");
        commentCreateProducer.commentCreate(om.registerModule(new JavaTimeModule()).writeValueAsString(commentCreate));
    }

//    @ServiceActivator(inputChannel = EVENT_GATEWAY_COMMENT_EDIT_CHANNEL)
//    public void send(KafkaCommentEdit kafkaCommentEdit) throws JsonProcessingException {
//        log.info("Router : kafkaCommentEdit");
//        commentCreateProducer.commentEdit(om.writeValueAsString(kafkaCommentEdit));
//    }
//
//    @ServiceActivator(inputChannel = EVENT_GATEWAY_COMMENT_Delete_CHANNEL)
//    public void send(KafkaCommentDelete kafkaCommentDelete) throws JsonProcessingException {
//        log.info("Router : kafkaCommentDelete");
//        commentCreateProducer.commentDelete(om.writeValueAsString(kafkaCommentDelete));
//    }
}
