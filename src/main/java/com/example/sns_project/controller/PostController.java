package com.example.sns_project.controller;

import com.example.sns_project.domain.post.dto.PostCreate;
import com.example.sns_project.domain.post.dto.PostResponse;
import com.example.sns_project.request.PostSearch;
import com.example.sns_project.response.ResponseDto;
import com.example.sns_project.service.PostService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> post(@RequestBody @Valid PostCreate postCreate, BindingResult bindingResult) {
        log.info("디버그 : {}", postCreate);
        postService.write(postCreate);
        return new ResponseEntity<>(new ResponseDto<>(1, "글 작성을 성공했습니다.", null), OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<?> get(@PathVariable Long postId) {
        final PostResponse postResponse = postService.get(postId);
        return new ResponseEntity<>(new ResponseDto<>(1, "글 조회에 성공했습니다.", postResponse), OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getList(@ModelAttribute PostSearch postSearch) {
        final List<PostResponse> postList = postService.getList(postSearch);
        return new ResponseEntity<>(new ResponseDto<>(1, "글 리스트 조회를 성공했습니다.", postList), HttpStatus.OK);
    }
}
