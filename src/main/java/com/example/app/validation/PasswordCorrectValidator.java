package com.example.app.validation;

import com.example.app.domain.user.UserService;
import com.example.app.domain.user.dto.UserCredentialsDto;
import com.example.app.domain.user.dto.UserManageAccountDto;
import com.example.app.domain.user.dto.UserSessionDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordCorrectValidator implements ConstraintValidator<PasswordCorrect, String> {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public PasswordCorrectValidator(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public void initialize(PasswordCorrect constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, final ConstraintValidatorContext context) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("to jest username z walidatora: " + username);
        UserCredentialsDto user = userService.findCredentialsByEmail(username).orElseThrow();

        boolean matches = passwordEncoder.matches(value, user.getPassword());
        System.out.println("old password: " + value);
        System.out.println("stare hasło: " + user.getPassword());
        System.out.println("Czy hasło pasuje: " + matches);
        return value == null || value.equals("") || matches;

    }
}
