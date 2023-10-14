package com.example.core.application.commands;

import com.example.core.config.aop.CommandAop;
import com.example.core.domain.messaging.event.Events;
import com.example.core.domain.post.PostRepository;
import com.example.core.domain.post.dto.PostResponse;
import com.example.core.domain.post.entity.Post;
import com.example.core.domain.post.entity.PostId;
import com.example.core.domain.post.exception.PostNotFound;
import com.example.core.domain.user.UserRepository;
import com.example.core.domain.user.entity.UserId;
import com.example.core.domain.user.exception.UserNotFound;
import com.example.core.interfaces.post.dto.PostCreate;
import com.example.core.interfaces.post.dto.PostEdit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.core.domain.messaging.MassagingVO.COMMAND_GATEWAY_POST_CREATE_CHANNEL;
import static com.example.core.domain.messaging.MassagingVO.MESSAGE_USER_ID;
import static java.util.stream.Collectors.toList;

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
    public void postCreate(Message<PostCreate> message) {
            final PostCreate postCreate = message.getPayload();
            final UserId userId = message.getHeaders().get(MESSAGE_USER_ID, UserId.class);

            var user = userRepository.findById(userId)
                    .orElseThrow(UserNotFound::new);
            final Post postNotValid = postCreate.toEntity();
            postNotValid.isValid();

            var post = postRepository.save(postNotValid);

            post.addUser(user.getUserId());
            user.addPost(post.getPostId());

            Events.register(
                    PostResponse.builder()
                            .postId(post.getPostId())
                            .content(post.getContent())
                            .title(post.getTitle())
                            .build());
    }



    @Transactional
    public PostResponse get(final PostId postId) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        return PostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    @Transactional
    public List<PostResponse> getList(Pageable pageable) {
        return postRepository.
                findAll(PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize())
                ).stream()
                .map(PostResponse::new)
                .collect(toList());
    }

    @CommandAop
    @Transactional
    public void edit(PostId id, PostEdit postEdit, UserId userId) {
        var post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        validateUserExists(userId);
        post.isSameUser(userId);

        post.editPost(
                postEdit.title(),
                postEdit.content()
        );
    }

    @CommandAop
    @Transactional
    public void delete(final PostId postId, UserId userId) {
        var post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        validateUserExists(userId);
        post.isSameUser(userId);

        postRepository.delete(post);
    }

    private void validateUserExists(final UserId userId) {
        userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
    }
}
