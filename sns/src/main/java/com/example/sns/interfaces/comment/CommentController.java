package com.example.sns.interfaces.comment;

import com.example.core.config.messaging.gateway.CommendGateway;
import com.example.core.domain.comment.dto.CommentCreate;
import com.example.core.domain.comment.dto.CommentEdit;
import com.example.core.domain.comment.entity.CommentId;
import com.example.core.domain.messaging.command.comment.CommentCreateMessage;
import com.example.core.domain.messaging.command.comment.CommentDeleteMessage;
import com.example.core.domain.messaging.command.comment.CommentEditMessage;
import com.example.core.domain.post.entity.PostId;
import com.example.core.infra.auth.LoginUser;
import com.example.core.infra.util.ResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    private final CommendGateway commendGateway;

    public CommentController(final CommendGateway commendGateway) {
        this.commendGateway = commendGateway;
    }
    // todo StatusCode 상황에 맞게 설계하기
    @PostMapping("/auth/posts/{postId}/comments")
    public ResponseEntity<ResponseDto<String>> comment(@ModelAttribute final PostId postId, @RequestBody @Valid CommentCreate commentCreate, BindingResult bindingResult
            , @AuthenticationPrincipal LoginUser loginUser)
    {
        commendGateway.request(new CommentCreateMessage(commentCreate, loginUser.getUser().getUserId(), postId));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.success());
    }

    @PostMapping("/auth/comments/{commentId}")
    public ResponseEntity<ResponseDto<String>> edit(@ModelAttribute CommentId commentId, @RequestBody CommentEdit commentEdit, @AuthenticationPrincipal LoginUser loginUser) {
        commendGateway.request(new CommentEditMessage(commentId, commentEdit, loginUser.getUser().getUserId()));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.success());
    }

    @DeleteMapping("/auth/comments/{commentId}")
    public ResponseEntity<ResponseDto<String>> delete(@ModelAttribute CommentId commentId, @AuthenticationPrincipal LoginUser loginUser) {
        commendGateway.request(new CommentDeleteMessage(commentId, loginUser.getUser().getUserId()));

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ResponseDto.success());
    }
}
