package com.example.app.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class UserCredentialsDto {
    private final String email;
    private final String password;
    private final Set<String> roles;
}
