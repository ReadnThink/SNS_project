package com.example.sns_project.service;

import com.example.sns_project.aop.ex.CustomApiException;
import com.example.sns_project.request.PostCreate;
import com.example.sns_project.domain.post.Post;
import com.example.sns_project.request.PostEdit;
import com.example.sns_project.response.PostResponse;
import com.example.sns_project.repository.PostRepository;
import com.example.sns_project.request.PostSearch;
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
        final Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomApiException("존재하지 않는 글입니다.")
        );

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
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomApiException("존재하지 않는 글입니다."));

        post.change(postEdit.getTitle(), postEdit.getContent());
    }

    public void delete(final Long postId) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomApiException("존재하지 않는 글입니다."));

        postRepository.delete(post);
    }
}
