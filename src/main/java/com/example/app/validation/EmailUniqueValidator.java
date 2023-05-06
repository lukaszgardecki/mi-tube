package com.example.app.validation;

import com.example.app.domain.user.UserService;
import com.example.app.domain.user.dto.UserCredentialsDto;
import com.example.app.domain.user.dto.UserSessionDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, String> {
    private final UserService userService;

    public EmailUniqueValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(EmailUnique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, final ConstraintValidatorContext context) {
        Optional<UserSessionDto> optionalUser = userService.findUserByEmail(value);
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (currentUserEmail.equals(value)) {
            return true;
        }
        return optionalUser.isEmpty();
    }
}
