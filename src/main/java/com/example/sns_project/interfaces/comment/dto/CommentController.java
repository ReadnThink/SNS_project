package com.example.sns_project.interfaces.comment.dto;

import com.example.sns_project.application.CommentService;
import com.example.sns_project.interfaces.gateway.CommendGateway;
import com.example.sns_project.domain.comment.dto.CommentResponse;
import com.example.sns_project.infra.auth.LoginUser;
import com.example.sns_project.infra.util.ResponseDto;
import com.example.sns_project.domain.comment.entity.CommentId;
import com.example.sns_project.domain.post.entity.PostId;
import com.example.sns_project.interfaces.comment.CommentEdit;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Executor;

@RestController
public class CommentController {
    private final CommentService commentService;
    private final CommendGateway commendGateway;
    private final Executor executor;

    public CommentController(final CommentService commentService, final CommendGateway commendGateway, @Qualifier("getDomainEventTaskExecutor") final Executor executor) {
        this.commentService = commentService;
        this.commendGateway = commendGateway;
        this.executor = executor;
    }

    @PostMapping("/auth/posts/{postId}/comments")
    public ResponseEntity<ResponseDto<CommentResponse>> comment(@ModelAttribute final PostId postId, @RequestBody @Valid CommentCreate commentCreate, BindingResult bindingResult
            , @AuthenticationPrincipal LoginUser loginUser)
    {
        executor.execute(()->
                commendGateway.request(commentCreate, loginUser.getUser().getUserId(), postId));
        return ResponseEntity.ok(ResponseDto.success());
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<ResponseDto<CommentResponse>> getComment(@ModelAttribute final CommentId commentId){
        final CommentResponse comment = commentService.getComment(commentId);
        return ResponseEntity.ok(ResponseDto.success(comment));
    }

    // todo 전체 댓글 리스트가 아닌 특정 댓글 리스트 API 필요
    @GetMapping("/comments")
    public ResponseEntity<ResponseDto<List<CommentResponse>>> commentList(Pageable pageable){
        final List<CommentResponse> commentResponseList = commentService.getList(pageable);

        return ResponseEntity.ok(ResponseDto.success(commentResponseList));
    }

    @PostMapping("/auth/comments/{commentId}")
    public ResponseEntity<ResponseDto<String>> edit(@ModelAttribute CommentId commentId, @RequestBody CommentEdit commentEdit, @AuthenticationPrincipal LoginUser loginUser) {
        final String success = commentService.edit(commentId, commentEdit, loginUser.getUser().getUserId());
        return ResponseEntity.ok(ResponseDto.success(success));
    }

    @DeleteMapping("/auth/comments/{commentId}")
    public ResponseEntity<ResponseDto<String>> delete(@ModelAttribute CommentId commentId, @AuthenticationPrincipal LoginUser loginUser) {
        final String success = commentService.delete(commentId, loginUser.getUser().getUserId());
        return ResponseEntity.ok(ResponseDto.success(success));
    }
}
