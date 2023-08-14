package com.example.sns_project.domain.user.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SignUp {

    private String email;
    private String password;
    private String name;
}
