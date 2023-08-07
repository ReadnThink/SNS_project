package com.example.sns_project.crypto;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
public class PlainPasswordEncoder implements PasswordEncoder {
    @Override
    public String encrypt(final String rawPassword) {
        return rawPassword;
    }

    @Override
    public boolean matches(final String rawPassword, final String encryptedPassword) {
        return rawPassword.equals(encryptedPassword);
    }
}
