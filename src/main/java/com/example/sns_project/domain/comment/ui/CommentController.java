package com.example.sns_project.domain.comment.ui;

import com.example.sns_project.domain.comment.application.CommentService;
import com.example.sns_project.domain.comment.dto.CommentCreate;
import com.example.sns_project.domain.comment.dto.CommentResponse;
import com.example.sns_project.global.config.auth.LoginUser;
import com.example.sns_project.global.util.ResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
