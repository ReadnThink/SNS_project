package com.example.sns.application.comment;

import com.example.core.config.aop.CommandAop;
import com.example.core.domain.comment.CommentRepository;
import com.example.core.domain.comment.entity.Comment;
import com.example.core.domain.comment.exception.CommentNotFound;
import com.example.core.domain.messaging.command.comment.CommentCreateMessage;
import com.example.core.domain.messaging.command.comment.CommentDeleteMessage;
import com.example.core.domain.messaging.command.comment.CommentEditMessage;
import com.example.core.domain.messaging.command.comment.kafka.KafkaCommentCreate;
import com.example.core.domain.messaging.event.Events;
import com.example.core.domain.post.PostRepository;
import com.example.core.domain.post.exception.PostNotFound;
import com.example.core.domain.user.UserRepository;
import com.example.core.domain.user.exception.UserNotFound;
import com.example.core.domain.user.exception.UserNotMatch;
import com.example.sns.infra.jpa.CommentRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.core.domain.messaging.MassagingVO.*;

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
    public void comment(CommentCreateMessage commentCreateMessage) {
        var commentCreate = commentCreateMessage.commentCreate();
        var userId = commentCreateMessage.userId();
        var postId = commentCreateMessage.postId();

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

        Events.register(
                new KafkaCommentCreate(
                        saveComment.getCommentId(),
                        saveComment.getPostId(),
                        saveComment.getAuthor(),
                        saveComment.getContent()
                )
        );
    }

    @CommandAop
    @Transactional
    @ServiceActivator(inputChannel = COMMAND_GATEWAY_COMMENT_EDIT_CHANNEL)
    public void edit(CommentEditMessage commentEditMessage) {
        var commentEdit = commentEditMessage.commentEdit();
        var commentId = commentEditMessage.commentId();
        var userId = commentEditMessage.userId();

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
    public void delete(CommentDeleteMessage commentDeleteMessage) {
        var commentId = commentDeleteMessage.commentId();
        var userId = commentDeleteMessage.userId();

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
