package com.example.query.interfaces.comment;

import com.example.core.domain.comment.dto.CommentResponse;
import com.example.core.domain.comment.entity.Comment;
import com.example.core.domain.comment.entity.CommentId;
import com.example.core.domain.comment.exception.CommentNotFound;
import com.example.core.infra.util.ResponseDto;
import com.example.query.infra.jpa.CommentQueryRepositoryImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class CommentQueryController {
    private final CommentQueryRepositoryImpl commentQueryRepository;

    public CommentQueryController(final CommentQueryRepositoryImpl commentQueryRepository) {
        this.commentQueryRepository = commentQueryRepository;
    }

    // todo StatusCode 상황에 맞게 설계하기
    @GetMapping("/comments/{commentId}")
    public ResponseEntity<ResponseDto<CommentResponse>> getComment(@ModelAttribute final CommentId commentId) {
        final Comment comment = commentQueryRepository.findById(commentId)
                .orElseThrow(CommentNotFound::new);

        return ResponseEntity.ok(ResponseDto.success(
                CommentResponse.builder()
                        .commentId(comment.getCommentId())
                        .comment(comment.getContent())
                        .author(comment.getAuthor())
                        .build()));
    }

    // todo 전체 댓글 리스트가 아닌 특정 댓글 리스트 API 필요
    @GetMapping("/comments")
    public ResponseEntity<ResponseDto<List<CommentResponse>>> commentList(Pageable pageable) {
        return ResponseEntity.ok(ResponseDto.success(
                commentQueryRepository.
                        findAll(PageRequest.of(
                                pageable.getPageNumber(),
                                pageable.getPageSize()))
                        .stream()
                        .map(CommentResponse::new)
                        .collect(toList())));
    }
}
