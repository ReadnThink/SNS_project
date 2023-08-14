package com.example.sns_project.domain.user.ui;

import com.example.sns_project.domain.user.dto.SignUp;
import com.example.sns_project.global.util.ResponseDto;
import com.example.sns_project.domain.user.application.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<String>> signUp(@RequestBody @Valid SignUp signUp, BindingResult br) {
        authService.signUp(signUp);

        return ResponseEntity.ok(ResponseDto.success());
    }

}
