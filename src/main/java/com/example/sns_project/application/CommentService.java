package com.example.sns_project.application;

import com.example.sns_project.domain.comment.CommentRepository;
import com.example.sns_project.domain.post.PostRepository;
import com.example.sns_project.domain.user.UserRepository;
import com.example.sns_project.infra.*;
import com.example.sns_project.domain.comment.dto.CommentCreate;
import com.example.sns_project.domain.comment.dto.CommentEdit;
import com.example.sns_project.domain.comment.dto.CommentResponse;
import com.example.sns_project.domain.comment.entity.Comment;
import com.example.sns_project.domain.comment.exception.CommentNotFound;
import com.example.sns_project.domain.post.exception.PostNotFound;
import com.example.sns_project.domain.user.exception.UserNotFound;
import com.example.sns_project.domain.user.exception.UserNotMatch;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.*;

@Service
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentService(final UserRepository userRepository, final PostRepository postRepository, final CommentRepositoryImpl commentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public CommentResponse comment(final Long postId, final CommentCreate commentCreate, final Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
        var post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        final Comment comment = Comment.builder()
                .author(user.getEmail())
                .content(commentCreate.content())
                .post(post)
                .build();

        final Comment savedComment = commentRepository.save(comment);

        return CommentResponse.builder()
                .commentId(savedComment.getId())
                .comment(savedComment.getContent())
                .author(savedComment.getAuthor())
                .build();
    }

    @Transactional
    public CommentResponse getComment(final Long commentId) {
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFound::new);

        return CommentResponse.builder()
                .commentId(comment.getId())
                .comment(comment.getContent())
                .author(comment.getAuthor())
                .build();
    }

    @Transactional
    public List<CommentResponse> getList(final Pageable pageable) {
        return commentRepository.findAll(pageable).stream()
                .map(CommentResponse::new)
                .sorted((c1, c2) -> c2.commentId().compareTo(c1.commentId()))
                .collect(toList());
    }


    @Transactional
    public String edit(final Long commentId, final CommentEdit commentEdit, final Long userId) {
        var comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFound::new);

        if (!comment.isSameUser(userId)){
            throw new UserNotMatch();
        }

        comment.editComment(commentEdit.comment());
        return "댓글 수정을 성공하였습니다.";
    }

    @Transactional
    public String delete(final Long commentId, final Long userId) {
        var comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFound::new);

        if (!comment.isSameUser(userId)){
            throw new UserNotMatch();
        }

        commentRepository.delete(comment);
        return "댓글 삭제를 성공하였습니다.";
    }
}
