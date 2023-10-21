package com.example.query.consumer;

import com.example.core.domain.messaging.command.post.kafka.KafkaPostCreate;
import com.example.query.infra.jpa.PostQueryRepositoryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PostConsumer {
    private final ObjectMapper om;
    private final PostQueryRepositoryImpl postQueryRepository;

    public PostConsumer(final PostQueryRepositoryImpl postQueryRepository, final ObjectMapper om) {
        this.postQueryRepository = postQueryRepository;
        this.om = om;
    }

    @KafkaListener(topics = "post_create", groupId = "group_1")
    @Transactional
    public void listener(String postCreate) throws JsonProcessingException {
        System.out.println("-----------------------------------after Mapping-----------------------------------");
        final KafkaPostCreate kafkaPostCreate = om.readValue(postCreate, KafkaPostCreate.class);
        postQueryRepository.save(kafkaPostCreate.toEntity());
        System.out.println("kafkaPostCreate : "+kafkaPostCreate);
    }
}
