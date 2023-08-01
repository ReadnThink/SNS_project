package com.example.sns_project.controller;

import com.example.sns_project.domain.post.dto.PostCreate;
import com.example.sns_project.domain.post.dto.PostOneResponse;
import com.example.sns_project.response.ResponseDto;
import com.example.sns_project.service.PostService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
public class PostController {

    private final PostService postService;

    public PostController(final PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
    public ResponseEntity<?> post(@RequestBody @Valid PostCreate postCreate, BindingResult bindingResult) {
        log.info("디버그 : {}", postCreate);
        postService.write(postCreate);
        return new ResponseEntity<>(new ResponseDto<>(1, "글 작성을 성공했습니다.", null), OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<?> get(@PathVariable Long postId) {
        final PostOneResponse postOneResponse = postService.get(postId);
        return new ResponseEntity<>(new ResponseDto<>(1, "글 조회에 성공했습니다.", postOneResponse), OK);
    }
}
