package com.example.sns_project.infra.jwt;

import com.example.sns_project.domain.user.dto.SignUp;
import com.example.sns_project.domain.user.entity.User;
import com.example.sns_project.interfaces.auth.dto.LoginRespDto;
import com.example.sns_project.infra.auth.LoginUser;
import com.example.sns_project.infra.util.CustomResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private final String secretKey;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, String secretKey) {
        super(authenticationManager);
        setFilterProcessesUrl("/login");
        this.authenticationManager = authenticationManager;
        this.secretKey = secretKey;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            ObjectMapper om = new ObjectMapper();
            SignUp loginRequest = om.readValue(request.getInputStream(), SignUp.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.email(), loginRequest.password()
            );

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;
        }catch (Exception e){
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        CustomResponseUtil.fail(response,"로그인실패", HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        LoginUser loginUser = (LoginUser) authResult.getPrincipal();
        User user = loginUser.getUser();
        String jwtToken = JwtProcess.create(loginUser, secretKey);
        response.addHeader(JwtVO.HEADER, jwtToken);

        LoginRespDto loginRespDto = LoginRespDto.builder()
                .id(user.getUserId())
                .email(user.getEmail())
                .build();

        CustomResponseUtil.success(response,loginRespDto);
    }

}
