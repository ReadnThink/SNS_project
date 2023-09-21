package com.example.sns_project.infra.message;

import com.example.sns_project.domain.post.PostRepository;
import com.example.sns_project.domain.post.dto.PostCreate;
import com.example.sns_project.domain.post.entity.Post;
import com.example.sns_project.domain.user.UserRepository;
import com.example.sns_project.domain.user.entity.UserId;
import com.example.sns_project.domain.user.exception.UserNotFound;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PostProcessingService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostProcessingService(final UserRepository userRepository, final PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Async
    @ServiceActivator(inputChannel = "createPostChannel")
    public void processCreatePostRequest(Message<PostCreate> message) {
        final PostCreate postCreate = message.getPayload();
        final UserId userId = message.getHeaders().get("userId", UserId.class);

        var user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
        final Post postNotValid = postCreate.toEntity();
        postNotValid.isValid();

        var post = postRepository.save(postNotValid);

        post.addUser(user.getUserId());
        user.addPost(post.getPostId());
    }
}
