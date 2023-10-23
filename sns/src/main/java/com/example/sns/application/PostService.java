package com.example.sns.application;

import com.example.core.config.aop.CommandAop;
import com.example.core.domain.messaging.command.post.PostCreateMessage;
import com.example.core.domain.messaging.command.post.PostDeleteMessage;
import com.example.core.domain.messaging.command.post.PostEditMessage;
import com.example.core.domain.messaging.command.post.kafka.KafkaPostCreate;
import com.example.core.domain.messaging.command.post.kafka.KafkaPostDelete;
import com.example.core.domain.messaging.command.post.kafka.KafkaPostEdit;
import com.example.core.domain.messaging.event.Events;
import com.example.core.domain.post.PostRepository;
import com.example.core.domain.post.dto.PostCreate;
import com.example.core.domain.post.entity.Post;
import com.example.core.domain.post.exception.PostNotFound;
import com.example.core.domain.user.UserRepository;
import com.example.core.domain.user.entity.UserId;
import com.example.core.domain.user.exception.UserNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.core.domain.messaging.MassagingVO.*;

@Slf4j
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(final PostRepository postRepository, final UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @CommandAop
    @ServiceActivator(inputChannel = COMMAND_GATEWAY_POST_CREATE_CHANNEL)
    public void postCreate(PostCreateMessage postCreateMessage) {
        log.info("postCreate");
        final PostCreate postCreate = postCreateMessage.postCreate();
        final UserId userId = postCreateMessage.userId();

        var user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
        final Post postNotValid = postCreate.toEntity();
        postNotValid.isValid();

        var post = postRepository.save(postNotValid);

        post.addUser(user.getUserId());
        user.addPost(post.getPostId());

        Events.register(
                new KafkaPostCreate(
                        post.getPostId(),
                        post.getUserId(),
                        post.getTitle(),
                        post.getContent()));
    }

    @CommandAop
    @Transactional
    @ServiceActivator(inputChannel = COMMAND_GATEWAY_POST_EDIT_CHANNEL)
    public void edit(PostEditMessage postEditMessage) {
        var postEdit = postEditMessage.postEdit();
        var postId = postEditMessage.postId();
        var userId = postEditMessage.userId();
        var post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        validateUserExists(userId);
        post.isSameUser(userId);

        post.editPost(
                postEdit.title(),
                postEdit.content()
        );

        Events.register(
                new KafkaPostEdit(
                        post.getPostId(),
                        post.getUserId(),
                        post.getTitle(),
                        post.getContent()));
    }

    @CommandAop
    @Transactional
    @ServiceActivator(inputChannel = COMMAND_GATEWAY_POST_DELETE_CHANNEL)
    public void delete(PostDeleteMessage postDeleteMessage) {
        var postId = postDeleteMessage.postId();
        var userId = postDeleteMessage.userId();

        var post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        validateUserExists(userId);
        post.isSameUser(userId);

        Events.register(
                new KafkaPostDelete(
                        post.getPostId(),
                        post.getTitle(),
                        post.getContent()));
        postRepository.delete(post);
    }

    private void validateUserExists(final UserId userId) {
        userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
    }
}
