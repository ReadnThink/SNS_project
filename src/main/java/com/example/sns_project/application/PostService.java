package com.example.sns_project.application;

import com.example.sns_project.domain.post.PostRepository;
import com.example.sns_project.domain.post.dto.PostCreate;
import com.example.sns_project.domain.post.dto.PostEdit;
import com.example.sns_project.domain.post.dto.PostResponse;
import com.example.sns_project.domain.post.entity.Post;
import com.example.sns_project.domain.post.exception.PostNotFound;
import com.example.sns_project.domain.user.UserRepository;
import com.example.sns_project.domain.user.entity.UserId;
import com.example.sns_project.domain.user.exception.UserNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public PostResponse write(PostCreate postCreate, final UserId userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
        final Post postNotValid = postCreate.toEntity();
        postNotValid.isValid();

        var post = postRepository.save(postNotValid);

        post.addUser(user);

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    @Transactional
    public PostResponse get(final Long postId) {
        final Post post = postRepository.findById(postId).orElseThrow(PostNotFound::new);

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    @Transactional
    public List<PostResponse> getList(Pageable pageable) {
        return postRepository.findAll(pageable).stream()
                .map(PostResponse::new)
                .sorted((c1, c2) -> c2.id().compareTo(c1.id()))
                .collect(toList());
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit, UserId userId) {
        var post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        validateUserExists(userId);
        post.isSameUser(userId);

        post.editPost(
                postEdit.title(),
                postEdit.content()
        );
    }

    @Transactional
    public void delete(final Long postId, UserId userId) {
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
