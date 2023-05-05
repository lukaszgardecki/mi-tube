package com.example.app.domain.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UserManageAccountDto {
    private String firstName;
    private String lastName;
    private String email;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
    private MultipartFile avatar;
}
