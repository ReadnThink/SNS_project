package com.example.sns.application;

import com.example.core.domain.messaging.command.post.KafkaPostCreate;
import com.example.core.domain.messaging.event.Events;
import com.example.core.domain.post.dto.PostCreate;
import com.example.core.domain.post.dto.PostResponse;
import com.example.core.domain.user.entity.UserId;
import com.example.core.domain.user.exception.UserNotFound;
import com.example.sns.infra.jpa.PostRepositoryImpl;
import com.example.core.infra.auth.UserRepositoryImpl;
import com.example.sns.interfaces.message.PostCreateProducer;
import org.springframework.stereotype.Service;

@Service
public class KafkaPostService {
    private final PostCreateProducer postCreateProducer;
    private final UserRepositoryImpl userRepository;
    private final PostRepositoryImpl postRepository;

    private int count = 0;

    public KafkaPostService(final PostCreateProducer postCreateProducer, final UserRepositoryImpl userRepository, final PostRepositoryImpl postRepository) {
        this.postCreateProducer = postCreateProducer;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public void apply(KafkaPostCreate kafkaPostCreate) {
        final PostCreate postCreate = kafkaPostCreate.postCreate();
        final UserId userId = kafkaPostCreate.userId();

        var user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
        var postBeforeValidCheck = postCreate.toEntity();
        postBeforeValidCheck.isValid();
        var post = postRepository.save(postBeforeValidCheck);

        post.addUser(user.getUserId());
        user.addPost(post.getPostId());

        Events.register(
                PostResponse.builder()
                        .postId(post.getPostId())
                        .content(post.getContent())
                        .title(post.getTitle())
                        .build());
//        count++;
//
//        if (count > 10) {
//            return;
//        }
//
//        postCreateProducer.create(kafkaPostCreate);
    }
}
