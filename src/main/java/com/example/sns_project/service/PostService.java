package com.example.sns_project.service;

import com.example.sns_project.aop.ex.CustomApiException;
import com.example.sns_project.domain.post.dto.PostCreate;
import com.example.sns_project.domain.post.Post;
import com.example.sns_project.domain.post.dto.PostOneResponse;
import com.example.sns_project.domain.post.dto.PostResponseDto;
import com.example.sns_project.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponseDto write(PostCreate postCreate) {
        final Post post = postRepository.save(postCreate.toEntity());
        return new PostResponseDto(post.getTitle(), post.getContent());
    }

    public PostOneResponse get(final Long postId) {
        final Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomApiException("존재하지 않는 글입니다.")
        );

        return PostOneResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

}
