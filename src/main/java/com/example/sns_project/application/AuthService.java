package com.example.sns_project.application;

import com.example.sns_project.domain.user.UserRepository;
import com.example.sns_project.domain.user.dto.SignUp;
import com.example.sns_project.domain.user.entity.User;
import com.example.sns_project.domain.user.entity.UserRole;
import com.example.sns_project.domain.user.exception.AlreadyExistsEmailException;
import com.example.sns_project.infra.UserRepositoryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(final UserRepositoryImpl userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void signUp(final SignUp signUp) {
        final Optional<User> optionalUser = userRepository.findByEmail(signUp.email());
        if (optionalUser.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        var user = User.builder()
                .name(signUp.name())
                .password(passwordEncoder.encode(signUp.password()))
                .email(signUp.email())
                .userRole(UserRole.USER)
                .build();

        userRepository.save(user);
    }
}
