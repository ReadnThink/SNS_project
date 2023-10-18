package com.example.sns.application;

import com.example.core.config.aop.CommandAop;
import com.example.core.domain.comment.CommentRepository;
import com.example.core.domain.comment.dto.CommentResponse;
import com.example.core.domain.comment.entity.Comment;
import com.example.core.domain.comment.entity.CommentId;
import com.example.core.domain.comment.exception.CommentNotFound;
import com.example.core.domain.messaging.command.comment.KafkaCommentCreate;
import com.example.core.domain.messaging.command.comment.KafkaCommentDelete;
import com.example.core.domain.messaging.command.comment.KafkaCommentEdit;
import com.example.core.domain.messaging.event.Events;
import com.example.core.domain.post.PostRepository;
import com.example.core.domain.post.entity.PostId;
import com.example.core.domain.post.exception.PostNotFound;
import com.example.core.domain.user.UserRepository;
import com.example.core.domain.user.entity.UserId;
import com.example.core.domain.user.exception.UserNotFound;
import com.example.core.domain.user.exception.UserNotMatch;
import com.example.core.domain.comment.dto.CommentCreate;
import com.example.core.domain.comment.dto.CommentEdit;
import com.example.sns.infra.jpa.CommentRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.core.domain.messaging.MassagingVO.*;
import static java.util.stream.Collectors.toList;

@Slf4j
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
    @CommandAop
    @ServiceActivator(inputChannel = COMMAND_GATEWAY_COMMENT_CREATE_CHANNEL)
    public void comment(KafkaCommentCreate kafkaCommentCreate) {
        var commentCreate = kafkaCommentCreate.commentCreate();
        var userId = kafkaCommentCreate.userId();
        var postId = kafkaCommentCreate.postId();

        var user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
        var post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        var saveComment = commentRepository.save(
                Comment.builder()
                .author(user.getEmail())
                .content(commentCreate.content())
                .postId(post.getPostId())
                .build());

        Events.register(CommentResponse.builder()
                .commentId(saveComment.getCommentId())
                .comment(saveComment.getContent())
                .author(saveComment.getAuthor())
                .lastModifiedAt(saveComment.getLastModifiedAt())
                .build());
    }

    @Transactional
    public CommentResponse getComment(final CommentId commentId) {
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFound::new);

        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .comment(comment.getContent())
                .author(comment.getAuthor())
                .build();
    }

    @Transactional
    public List<CommentResponse> getList(final Pageable pageable) {
        return commentRepository.
                findAll(PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize())
                )
                .stream()
                .map(CommentResponse::new)
                .collect(toList());
    }

    @CommandAop
    @Transactional
    @ServiceActivator(inputChannel = COMMAND_GATEWAY_COMMENT_EDIT_CHANNEL)
    public void edit(KafkaCommentEdit kafkaCommentEdit) {
        var commentEdit = kafkaCommentEdit.commentEdit();
        var commentId = kafkaCommentEdit.commentId();
        var userId = kafkaCommentEdit.userId();

        var comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFound::new);
        var user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);

        if (!comment.isSameUser(user.getEmail())) {
            throw new UserNotMatch();
        }

        comment.editComment(commentEdit.comment());
    }


    @CommandAop
    @Transactional
    @ServiceActivator(inputChannel = COMMAND_GATEWAY_COMMENT_DELETE_CHANNEL)
    public void delete(KafkaCommentDelete kafkaCommentDelete) {
        var commentId = kafkaCommentDelete.commentId();
        var userId = kafkaCommentDelete.userId();

        var comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFound::new);
        var user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);

        if (!comment.isSameUser(user.getEmail())) {
            throw new UserNotMatch();
        }

        commentRepository.delete(comment);
    }
}
