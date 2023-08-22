package com.example.sns_project.interfaces;

import com.example.sns_project.application.PostService;
import com.example.sns_project.domain.post.dto.PostCreate;
import com.example.sns_project.domain.post.dto.PostEdit;
import com.example.sns_project.domain.post.dto.PostResponse;
import com.example.sns_project.domain.post.dto.PostSearch;
import com.example.sns_project.config.auth.LoginUser;
import com.example.sns_project.config.util.ResponseDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class PostController {

    private final PostService postService;

    public PostController(final PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/auth/posts")
    public ResponseEntity<ResponseDto<PostResponse>> post(@RequestBody @Valid PostCreate postCreate, BindingResult bindingResult
            , @AuthenticationPrincipal LoginUser loginUser)
    {

        final PostResponse write = postService.write(postCreate, loginUser.getUser().getId());
        return ResponseEntity.ok(ResponseDto.success(write));
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<ResponseDto<PostResponse>> get(@PathVariable Long postId) {
        final PostResponse postResponse = postService.get(postId);

        return ResponseEntity.ok(ResponseDto.success(postResponse));
    }

    @GetMapping("/posts")
    public ResponseEntity<ResponseDto<List<PostResponse>>> getList(@ModelAttribute PostSearch postSearch) {
        final List<PostResponse> postList = postService.getList(postSearch);

        return ResponseEntity.ok(ResponseDto.success(postList));
    }

    @PostMapping("/auth/posts/{postId}")
    public ResponseEntity<ResponseDto<String>> edit(@PathVariable Long postId, @RequestBody PostEdit postEdit
            , @AuthenticationPrincipal LoginUser loginUser)
    {
        postService.edit(postId, postEdit, loginUser.getUser().getId());

        return ResponseEntity.ok(ResponseDto.success());
    }

    @DeleteMapping("/auth/posts/{postId}")
    public ResponseEntity<ResponseDto<String>> delete(@PathVariable Long postId, @AuthenticationPrincipal LoginUser loginUser) {
        postService.delete(postId, loginUser.getUser().getId());

        return ResponseEntity.ok(ResponseDto.success());
    }
}
