package com.example.sns.application;

import com.example.core.domain.messaging.command.post.PostCreateMessage;
import com.example.sns.interfaces.message.PostCreateProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

//todo 클래스 삭제
@Service
public class KafkaPostService {
    private final PostCreateProducer postCreateProducer;
    private final ObjectMapper om;

    private int count = 0;

    public KafkaPostService(final PostCreateProducer postCreateProducer, final ObjectMapper om) {
        this.postCreateProducer = postCreateProducer;
        this.om = om;
    }

    public void apply(PostCreateMessage postCreateMessage) throws JsonProcessingException {
//        final PostCreate postCreate = kafkaPostCreate.postCreate();
//        final UserId userId = kafkaPostCreate.userId();
//
//        var user = userRepository.findById(userId)
//                .orElseThrow(UserNotFound::new);
//        var postBeforeValidCheck = postCreate.toEntity();
//        postBeforeValidCheck.isValid();
//        var post = postRepository.save(postBeforeValidCheck);
//
//        post.addUser(user.getUserId());
//        user.addPost(post.getPostId());
//
//        Events.register(
//                PostResponse.builder()
//                        .postId(post.getPostId())
//                        .content(post.getContent())
//                        .title(post.getTitle())
//                        .build());
        System.out.println("in kafkaPostService");
        final String data = om.writeValueAsString(postCreateMessage);
        postCreateProducer.postCreate(data);
//        count++;
//
//        if (count > 10) {
//            return;
//        }
//
//        postCreateProducer.create(kafkaPostCreate);
    }
}
