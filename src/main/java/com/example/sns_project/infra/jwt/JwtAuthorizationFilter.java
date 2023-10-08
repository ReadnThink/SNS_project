package com.example.sns_project.infra.jwt;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.sns_project.infra.auth.LoginUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final String secretKey;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, String secretKey) {
        super(authenticationManager);
        this.secretKey = secretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(isHeaderVerify(request,response)){
            try {
                String token = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
                LoginUser loginUser = JwtProcess.verify(token, secretKey);

                Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (TokenExpiredException e) {
                e.printStackTrace();
            }
        }
        chain.doFilter(request,response);
    }

    private boolean isHeaderVerify(HttpServletRequest request, HttpServletResponse response) {

        String header = request.getHeader(JwtVO.HEADER);
        return header != null && header.startsWith(JwtVO.TOKEN_PREFIX);
    }
}
