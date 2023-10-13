package com.example.sns.interfaces.comment;

import com.example.sns.application.commands.CommentService;
import com.example.sns.domain.comment.dto.CommentResponse;
import com.example.sns.domain.comment.entity.CommentId;
import com.example.sns.domain.post.entity.PostId;
import com.example.sns.infra.auth.LoginUser;
import com.example.sns.infra.util.ResponseDto;
import com.example.sns.interfaces.comment.dto.CommentCreate;
import com.example.sns.interfaces.comment.dto.CommentEdit;
import com.example.sns.interfaces.gateway.CommendGateway;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    private final CommentService commentService;
    private final CommendGateway commendGateway;

    public CommentController(final CommentService commentService, final CommendGateway commendGateway) {
        this.commentService = commentService;
        this.commendGateway = commendGateway;
    }

    @PostMapping("/auth/posts/{postId}/comments")
    public ResponseEntity<ResponseDto<CommentResponse>> comment(@ModelAttribute final PostId postId, @RequestBody @Valid CommentCreate commentCreate, BindingResult bindingResult
            , @AuthenticationPrincipal LoginUser loginUser)
    {
        commendGateway.request(commentCreate, loginUser.getUser().getUserId(), postId);

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
