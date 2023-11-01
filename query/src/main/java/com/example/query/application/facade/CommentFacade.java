package com.example.query.application.facade;

import com.example.core.domain.comment.dto.CommentResponse;
import com.example.core.domain.comment.entity.Comment;
import com.example.core.domain.comment.entity.CommentId;
import com.example.core.domain.comment.exception.CommentNotFound;
import com.example.core.domain.post.dto.PostResponse;
import com.example.query.infra.jpa.CommentQueryRepositoryImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CommentFacade {
    private final CommentQueryRepositoryImpl commentQueryRepository;

    public CommentFacade(final CommentQueryRepositoryImpl commentQueryRepository) {
        this.commentQueryRepository = commentQueryRepository;
    }

    public CommentResponse get(CommentId commentId) {
        final Comment comment = commentQueryRepository.findById(commentId)
                .orElseThrow(CommentNotFound::new);

        return CommentResponse.builder()
                        .commentId(comment.getCommentId())
                        .comment(comment.getContent())
                        .author(comment.getAuthor())
                        .build();
    }

    public List<CommentResponse> getList(Pageable pageable) {
        return commentQueryRepository.
                findAll(PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by("createdAt").descending()))
                ).stream()
                .map(CommentResponse::new)
                .collect(toList());
    }
}
