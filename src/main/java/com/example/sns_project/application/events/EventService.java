package com.example.sns_project.application.events;

import com.example.sns_project.domain.comment.dto.CommentResponse;
import com.example.sns_project.domain.post.dto.PostResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.sns_project.domain.messaging.MassagingVO.EVENT_GATEWAY_COMMENT_GET_CHANNEL;
import static com.example.sns_project.domain.messaging.MassagingVO.EVENT_GATEWAY_POST_GET_CHANNEL;

/**
 * Message 객체로 안감싸고 DTO로 받로 받아올 수 있는것을 확인
 */
@Slf4j
@Service
public class EventService {

    @Transactional(readOnly = true)
    @ServiceActivator(inputChannel = EVENT_GATEWAY_POST_GET_CHANNEL)
    public PostResponse PostGet(PostResponse postResponse) {
        return postResponse;
    }

    @Transactional(readOnly = true)
    @ServiceActivator(inputChannel = EVENT_GATEWAY_COMMENT_GET_CHANNEL)
    public CommentResponse CommentGet(CommentResponse commentResponse) {
        return commentResponse;
    }
}
