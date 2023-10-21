package com.example.query.interfaces.post;

import com.example.core.domain.post.dto.PostResponse;
import com.example.core.domain.post.entity.Post;
import com.example.core.domain.post.entity.PostId;
import com.example.core.domain.post.exception.PostNotFound;
import com.example.core.infra.util.ResponseDto;
import com.example.query.infra.jpa.PostQueryRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
public class PostQueryController {
    private final PostQueryRepositoryImpl postQueryRepository;

    public PostQueryController(final PostQueryRepositoryImpl postQueryRepository) {
        this.postQueryRepository = postQueryRepository;
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<ResponseDto<PostResponse>> get(@ModelAttribute PostId postId) {
        log.info("\"/posts\" get");
        final Post post = postQueryRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        return ResponseEntity.ok(ResponseDto.success(
                PostResponse.builder()
                        .postId(post.getPostId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .build()));
    }

    @GetMapping("/posts")
    public ResponseEntity<ResponseDto<List<PostResponse>>> getList(Pageable pageable) {
        log.info("\"/posts\" getList");
        return ResponseEntity.ok(ResponseDto.success(
                postQueryRepository.
                        findAll(PageRequest.of(
                                pageable.getPageNumber(),
                                pageable.getPageSize())
                        ).stream()
                        .map(PostResponse::new)
                        .collect(toList())));
    }
}
