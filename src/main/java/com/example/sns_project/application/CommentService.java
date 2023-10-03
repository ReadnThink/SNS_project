package com.example.sns_project.application;

import com.example.sns_project.application.aop.CommandAop;
import com.example.sns_project.config.messaging.event.Events;
import com.example.sns_project.domain.comment.CommentRepository;
import com.example.sns_project.domain.comment.dto.CommentCreate;
import com.example.sns_project.domain.comment.dto.CommentEdit;
import com.example.sns_project.domain.comment.dto.CommentResponse;
import com.example.sns_project.domain.comment.entity.Comment;
import com.example.sns_project.domain.comment.entity.CommentId;
import com.example.sns_project.domain.comment.exception.CommentNotFound;
import com.example.sns_project.domain.post.PostRepository;
import com.example.sns_project.domain.post.entity.PostId;
import com.example.sns_project.domain.post.exception.PostNotFound;
import com.example.sns_project.domain.user.UserRepository;
import com.example.sns_project.domain.user.entity.UserId;
import com.example.sns_project.domain.user.exception.UserNotFound;
import com.example.sns_project.domain.user.exception.UserNotMatch;
import com.example.sns_project.infra.jpa.CommentRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.sns_project.config.messaging.MassagingVO.MESSAGE_POST_ID;
import static com.example.sns_project.config.messaging.MassagingVO.MESSAGE_USER_ID;
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
    @ServiceActivator(inputChannel = "CommentCreate")
    public void comment(Message<CommentCreate> message) {
        var commentCreate = message.getPayload();
        var userId = message.getHeaders().get(MESSAGE_USER_ID, UserId.class);
        var postId = message.getHeaders().get(MESSAGE_POST_ID, PostId.class);

        var user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
        var post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        final Comment comment = Comment.builder()
                .author(user.getEmail())
                .content(commentCreate.content())
                .postId(post.getPostId())
                .build();
        final Comment saveComment = commentRepository.save(comment);
        Events.register(saveComment);
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


    @Transactional
    public String edit(final CommentId commentId, final CommentEdit commentEdit, final UserId userId) {
        var comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFound::new);
        var user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);

        if (!comment.isSameUser(user.getEmail())) {
            throw new UserNotMatch();
        }

        comment.editComment(commentEdit.comment());
        return "댓글 수정을 성공하였습니다.";
    }

    @Transactional
    public String delete(final CommentId commentId, final UserId userId) {
        var comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFound::new);
        var user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);

        if (!comment.isSameUser(user.getEmail())) {
            throw new UserNotMatch();
        }

        commentRepository.delete(comment);
        return "댓글 삭제를 성공하였습니다.";
    }
}
