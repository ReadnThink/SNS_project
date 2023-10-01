package com.example.sns_project.application.gateway;

import com.example.sns_project.config.messaging.command.Command;
import com.example.sns_project.domain.post.entity.PostId;
import com.example.sns_project.domain.user.entity.UserId;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;

import static com.example.sns_project.config.messaging.command.CommandConfig.*;

/**
 * return type에 대항 응답 차이
 *
 * void면 api응답을 바로한다.
 * 그런데 제네릭을 사용하면 api응답이 계속 지연된다...??
 */
@MessagingGateway(defaultRequestChannel = COMMAND_GATEWAY_CHANNEL)
public interface CommendGateway {
    void request(Command command, @Header(value = COMMAND_POST_USERID) UserId userId);
    void request(Command command, @Header(value = COMMAND_POST_USERID) UserId userId, @Header(value = COMMAND_COMMENT_POSTID) PostId postId);
}
