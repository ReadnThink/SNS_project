package com.example.sns_project.service;

import com.example.sns_project.domain.post.dto.PostCreate;
import com.example.sns_project.domain.post.Post;
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
        log.info("디버그 : service.writeaa : {}",postCreate);
        final Post post = postRepository.save(postCreate.toEntity());
        return new PostResponseDto(post.getTitle(), post.getContent());
    }
}
