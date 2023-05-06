package com.example.app.domain.user.dto;

import com.example.app.validation.EmailUnique;
import com.example.app.validation.FieldMatch;
import com.example.app.validation.PasswordCorrect;
import com.example.app.validation.PasswordLength;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@FieldMatch(first = "newPassword", second = "confirmPassword", message = "The password fields must match")
@PasswordLength(
        first = "newPassword",
        second = "confirmPassword",
        third = "oldPassword",
        min = 8, max = 200,
        message = "The new password must be at least {min} characters long"
)
public class UserManageAccountDto {
    @Size(min = 1)
    private String firstName;
    @Size(min = 1)
    private String lastName;
    @Email
    @EmailUnique(message = "An account using this email address already exists")
    private String email;
    @PasswordCorrect(message = "Old Password is incorrect")
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
    private MultipartFile avatar;
}
