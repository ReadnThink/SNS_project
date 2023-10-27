package com.example.sns.application;

import com.example.core.domain.messaging.command.post.kafka.KafkaPostCreate;
import com.example.core.domain.messaging.command.post.kafka.KafkaPostDelete;
import com.example.core.domain.messaging.command.post.kafka.KafkaPostEdit;
import com.example.sns.interfaces.message.PostCreateProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import static com.example.core.domain.messaging.MassagingVO.*;

@Slf4j
@Service
public class KafkaSendRouter {
    private final PostCreateProducer postCreateProducer;
    private final ObjectMapper om;

    public KafkaSendRouter(final PostCreateProducer postCreateProducer, final ObjectMapper om) {
        this.postCreateProducer = postCreateProducer;
        this.om = om;
    }

    @ServiceActivator(inputChannel = EVENT_GATEWAY_POST_CREATE_CHANNEL)
    public void send(KafkaPostCreate kafkaPostCreate) throws JsonProcessingException {
        log.info("Router : kafkaPostCreate");
        postCreateProducer.postCreate(om.writeValueAsString(kafkaPostCreate));
    }

    @ServiceActivator(inputChannel = EVENT_GATEWAY_POST_EDIT_CHANNEL)
    public void send(KafkaPostEdit kafkaPostEdit) throws JsonProcessingException {
        log.info("Router : kafkaPostEdit");
        postCreateProducer.postEdit(om.writeValueAsString(kafkaPostEdit));
    }

    @ServiceActivator(inputChannel = EVENT_GATEWAY_POST_DELETE_CHANNEL)
    public void send(KafkaPostDelete kafkaPostDelete) throws JsonProcessingException {
        log.info("Router : kafkaPostDelete");
        postCreateProducer.postDelete(om.writeValueAsString(kafkaPostDelete));
    }
}
