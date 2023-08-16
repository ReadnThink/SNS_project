package com.example.sns_project.domain.comment.ui;

import com.example.sns_project.domain.comment.application.CommentService;
import com.example.sns_project.domain.comment.dto.CommentCreate;
import com.example.sns_project.domain.comment.dto.CommentEdit;
import com.example.sns_project.domain.comment.dto.CommentResponse;
import com.example.sns_project.global.config.auth.LoginUser;
import com.example.sns_project.global.util.ResponseDto;
import jakarta.validation.Valid;
import lombok.Builder;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(final CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/auth/posts/{postId}/comments")
    public ResponseEntity<ResponseDto<CommentResponse>> comment(@PathVariable final Long postId, @RequestBody @Valid CommentCreate commentCreate, BindingResult bindingResult
            , @AuthenticationPrincipal LoginUser loginUser)
    {
        final CommentResponse comment = commentService.comment(postId, commentCreate, loginUser.getUser().getId());

        return ResponseEntity.ok(ResponseDto.success(comment));
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<ResponseDto<CommentResponse>> comment(@PathVariable final Long commentId){
        final CommentResponse comment = commentService.getComment(commentId);

        return ResponseEntity.ok(ResponseDto.success(comment));
    }

    @GetMapping("/comments")
    public ResponseEntity<ResponseDto<List<CommentResponse>>> commentList(Pageable pageable){
        final List<CommentResponse> commentResponseList = commentService.getList(pageable);

        return ResponseEntity.ok(ResponseDto.success(commentResponseList));
    }

    @PostMapping("/auth/comments/{commentId}")
    public ResponseEntity<ResponseDto<String>> edit(@PathVariable Long commentId, @RequestBody CommentEdit commentEdit, @AuthenticationPrincipal LoginUser loginUser) {
        final String success = commentService.edit(commentId, commentEdit, loginUser.getUser().getId());
        return ResponseEntity.ok(ResponseDto.success(success));
    }

    @DeleteMapping("/auth/comments/{commentId}")
    public ResponseEntity<ResponseDto<String>> delete(@PathVariable Long commentId, @AuthenticationPrincipal LoginUser loginUser) {
        final String success = commentService.delete(commentId, loginUser.getUser().getId());
        return ResponseEntity.ok(ResponseDto.success(success));
    }
}
