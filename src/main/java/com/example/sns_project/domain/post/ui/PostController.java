package com.example.sns_project.domain.post.ui;

import com.example.sns_project.domain.post.application.PostService;
import com.example.sns_project.domain.post.dto.PostCreate;
import com.example.sns_project.domain.post.dto.PostEdit;
import com.example.sns_project.domain.post.dto.PostResponse;
import com.example.sns_project.domain.post.dto.PostSearch;
import com.example.sns_project.global.util.ResponseDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
public class PostController {

    private final PostService postService;

    public PostController(final PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
    public ResponseEntity<ResponseDto<String>> post(@RequestBody @Valid PostCreate postCreate, BindingResult bindingResult) {
        postCreate.isValid();
        postService.write(postCreate);
        return ResponseEntity.ok(ResponseDto.success());
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

    @PostMapping("/posts/{postId}")
    public ResponseEntity<ResponseDto<String>> edit(@PathVariable Long postId, @RequestBody PostEdit postEdit) {
        postService.edit(postId, postEdit);

        return ResponseEntity.ok(ResponseDto.success());
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ResponseDto<String>> delete(@PathVariable Long postId) {
        postService.delete(postId);

        return ResponseEntity.ok(ResponseDto.success());
    }
}
