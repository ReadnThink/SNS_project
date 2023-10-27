package com.example.sns.interfaces.post;

import com.example.core.config.messaging.gateway.CommendGateway;
import com.example.core.domain.messaging.command.post.PostCreateMessage;
import com.example.core.domain.messaging.command.post.PostDeleteMessage;
import com.example.core.domain.messaging.command.post.PostEditMessage;
import com.example.core.domain.post.dto.PostCreate;
import com.example.core.domain.post.dto.PostEdit;
import com.example.core.domain.post.dto.PostResponse;
import com.example.core.domain.post.entity.PostId;
import com.example.core.infra.auth.LoginUser;
import com.example.core.infra.util.ResponseDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class PostController {
    private final CommendGateway commendGateway;

    public PostController(final CommendGateway commendGateway) {
        this.commendGateway = commendGateway;
    }

    @PostMapping("/auth/posts")
    public ResponseEntity<ResponseDto<PostResponse>> post(@RequestBody @Valid PostCreate postCreate, BindingResult bindingResult
            , @AuthenticationPrincipal LoginUser loginUser)
    {
        log.info("Controller! - for Thread check");
        commendGateway.request(new PostCreateMessage(postCreate, loginUser.getUser().getUserId()));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.success());
    }

    @PostMapping("/auth/posts/{postId}")
    public ResponseEntity<ResponseDto<String>> edit(@ModelAttribute PostId postId, @RequestBody PostEdit postEdit
            , @AuthenticationPrincipal LoginUser loginUser)
    {
        commendGateway.request(new PostEditMessage(postEdit, postId, loginUser.getUser().getUserId()));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.success());
    }

    @DeleteMapping("/auth/posts/{postId}")
    public ResponseEntity<ResponseDto<String>> delete(@ModelAttribute PostId postId, @AuthenticationPrincipal LoginUser loginUser) {
        commendGateway.request(new PostDeleteMessage(postId, loginUser.getUser().getUserId()));

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ResponseDto.success());
    }
}
