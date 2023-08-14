package com.example.sns_project.domain.user.ui;

import com.example.sns_project.domain.user.dto.SignUp;
import com.example.sns_project.domain.post.dto.ResponseDto;
import com.example.sns_project.domain.user.application.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

/**
 * commit : 인증하기 위한 AuthController 생성
 */

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<ResponseDto<Object>> signUp(@RequestBody SignUp signUp) {
        authService.signUp(signUp);

        return new ResponseEntity<>(ResponseDto.builder()
                .code(1)
                .message("회원가입을 성공했습니다.")
                .build(), OK);
    }

}
