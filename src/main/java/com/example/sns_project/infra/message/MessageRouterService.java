package com.example.sns_project.infra.message;

import com.example.sns_project.domain.post.dto.PostCreate;
import com.example.sns_project.domain.user.entity.UserId;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class MessageRouterService {
    private final MessageChannel createPostChannel;

    public MessageRouterService(final MessageChannel createPostChannel) {
        this.createPostChannel = createPostChannel;
    }

    public void routeCreatePostRequest(PostCreate postCreate, UserId userId) {
        createPostChannel
                .send(MessageBuilder
                        .withPayload(postCreate)
                        .setHeader("userId", userId)
                .build());
    }
}
