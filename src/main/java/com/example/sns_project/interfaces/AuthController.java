package com.example.sns_project.interfaces;

import com.example.sns_project.application.AuthService;
import com.example.sns_project.domain.user.dto.SignUp;
import com.example.sns_project.config.util.ResponseDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * commit : 인증하기 위한 AuthController 생성
 */

@Slf4j
@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<String>> signUp(@RequestBody @Valid SignUp signUp, BindingResult br) {
        authService.signUp(signUp);

        return ResponseEntity.ok(ResponseDto.success());
    }

}
