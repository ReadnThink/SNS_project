package com.example.sns.infra.auth;

import com.example.sns.config.handler.Http401Handler;
import com.example.sns.config.handler.Http403Handler;
import com.example.sns.domain.user.UserRepository;
import com.example.sns.domain.user.entity.User;
import com.example.sns.domain.user.entity.UserRole;
import com.example.sns.infra.jwt.JwtAuthenticationFilter;
import com.example.sns.infra.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@RequiredArgsConstructor
public class SpringSecurityConfig {

    @Value("${jwt.secret}")
    private String secretKey;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttpRequests  -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/auth/**")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole(""+ UserRole.ADMIN)
                        .anyRequest().permitAll())
                .addFilterBefore(new JwtAuthenticationFilter(authenticationManager(), secretKey), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthorizationFilter(authenticationManager(), secretKey), BasicAuthenticationFilter.class)
                .exceptionHandling(e -> {
                    e.accessDeniedHandler(new Http403Handler());
                    e.authenticationEntryPoint(new Http401Handler());
                })
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService(userRepository));
        provider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(provider);
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));

            return new LoginUser(user);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
