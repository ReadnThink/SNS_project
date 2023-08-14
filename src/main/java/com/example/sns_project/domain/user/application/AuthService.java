package com.example.sns_project.domain.user.application;

import com.example.sns_project.domain.user.dao.UserRepository;
import com.example.sns_project.domain.user.dto.SignUp;
import com.example.sns_project.domain.user.entity.User;
import com.example.sns_project.domain.user.entity.UserRole;
import com.example.sns_project.domain.user.exception.AlreadyExistsEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public void signUp(final SignUp signUp) {
        final Optional<User> optionalUser = userRepository.findByEmail(signUp.getEmail());
        if (optionalUser.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        var user = User.builder()
                .name(signUp.getName())
                .password(passwordEncoder.encode(signUp.getPassword()))
                .email(signUp.getEmail())
                .userRole(UserRole.USER)
                .build();

        userRepository.save(user);
    }
}
