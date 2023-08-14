package com.example.sns_project.domain.post.application;

import com.example.sns_project.domain.post.exception.PostNotFound;
import com.example.sns_project.domain.post.dto.PostCreate;
import com.example.sns_project.domain.post.entity.Post;
import com.example.sns_project.domain.post.dto.PostEdit;
import com.example.sns_project.domain.post.dto.PostResponse;
import com.example.sns_project.domain.post.dao.PostRepository;
import com.example.sns_project.domain.post.dto.PostSearch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponse write(PostCreate postCreate) {
        final Post post = postRepository.save(postCreate.toEntity());
        return new PostResponse(post.getTitle(), post.getContent());
    }

    public PostResponse get(final Long postId) {
        final Post post = postRepository.findById(postId).orElseThrow(PostNotFound::new);

        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public List<PostResponse> getList(PostSearch postSearch) {
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id).orElseThrow((PostNotFound::new));

        post.change(postEdit.getTitle(), postEdit.getContent());
    }

    public void delete(final Long postId) {
        final Post post = postRepository.findById(postId).orElseThrow((PostNotFound::new));

        postRepository.delete(post);
    }
}
