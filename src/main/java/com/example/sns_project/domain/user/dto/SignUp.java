package com.example.sns_project.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SignUp {

    @NotBlank
    @Email
    private String email;

    @Size(min = 8)
    private String password;

    @NotBlank
    private String name;
}
