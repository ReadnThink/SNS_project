package com.example.sns.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SignUp(
        @NotBlank
        @Email
        String email,

        @Size(min = 8, max = 20)
        String password,

        @NotBlank
        String name
) {
}
