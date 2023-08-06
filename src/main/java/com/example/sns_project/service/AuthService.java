package com.example.sns_project.service;

import com.example.sns_project.domain.user.Session;
import com.example.sns_project.domain.user.User;
import com.example.sns_project.exception.InvalidSigninInformation;
import com.example.sns_project.repository.UserRepository;
import com.example.sns_project.request.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public Long signIn(Login request) {
        final User user = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(InvalidSigninInformation::new);

        final Session session = user.addSession();
        return user.getId();
    }
}
