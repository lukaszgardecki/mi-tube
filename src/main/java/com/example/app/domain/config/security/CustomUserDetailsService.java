package com.example.app.domain.config.security;

import com.example.app.domain.user.UserService;
import com.example.app.domain.user.dto.UserCredentialsDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findCredentialsByEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User with email %s not found".formatted(username)));
    }

    private UserDetails createUserDetails(UserCredentialsDto userCredentialsDto) {
        return User.builder()
                .username(userCredentialsDto.getEmail())
                .password(userCredentialsDto.getPassword())
                .roles(userCredentialsDto.getRoles().toArray(String[]::new))
                .build();
    }
}
