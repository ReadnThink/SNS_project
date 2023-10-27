package com.example.query.application;

import com.example.core.domain.post.dto.PostResponse;
import com.example.core.domain.post.entity.Post;
import com.example.core.domain.post.entity.PostId;
import com.example.core.domain.post.exception.PostNotFound;
import com.example.query.infra.jpa.PostQueryRepositoryImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PostFacade {
    private final PostQueryRepositoryImpl postQueryRepository;

    public PostFacade(final PostQueryRepositoryImpl postQueryRepository) {
        this.postQueryRepository = postQueryRepository;
    }

    public PostResponse get(PostId postId) {
        final Post post = postQueryRepository.findById(postId)
                .orElseThrow(PostNotFound::new);
        return PostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public List<PostResponse> getList(Pageable pageable) {
        return postQueryRepository.
                findAll(PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by("createdAt").descending()))
                ).stream()
                .map(PostResponse::new)
                .collect(toList());
    }
}
