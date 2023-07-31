package com.example.sns_project.controller;

import com.example.sns_project.domain.post.dto.PostCreate;
import com.example.sns_project.response.ResponseDto;
import com.example.sns_project.service.PostService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        return new ResponseEntity<>(new ResponseDto<>(1, "글작성에 성공했습니다.", null), HttpStatus.OK);
    }

}
