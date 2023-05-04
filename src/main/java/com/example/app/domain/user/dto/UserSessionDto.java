package com.example.app.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSessionDto {
    private String firstName;
    private String lastName;
    private String initials;
    private String avatar;
}
