package com.example.sns_project.service;

import com.example.sns_project.crypto.PasswordEncoder;
import com.example.sns_project.domain.user.User;
import com.example.sns_project.exception.AlreadyExistsEmailException;
import com.example.sns_project.exception.InvalidSigninInformation;
import com.example.sns_project.repository.UserRepository;
import com.example.sns_project.request.Login;
import com.example.sns_project.request.SignUp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public Long login(Login login) {
        final User user = userRepository.findByEmail(login.getEmail())
                .orElseThrow(InvalidSigninInformation::new);

        var matches= encoder.matches(login.getPassword(), user.getPassword());
        if (!matches) {
            throw new InvalidSigninInformation();
        }

        return user.getId();
    }

    public void signUp(final SignUp signUp) {
        final Optional<User> optionalUser = userRepository.findByEmail(signUp.getEmail());
        if (optionalUser.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        final String encodedPassword = encoder.encrypt(signUp.getPassword());

        var user = User.builder()
                .name(signUp.getName())
                .password(encodedPassword)
                .email(signUp.getEmail())
                .build();

        userRepository.save(user);
    }
}
