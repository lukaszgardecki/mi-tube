package com.example.app.domain.config.security;

import com.example.app.domain.user.dto.UserCredentialsDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public void updateAuthentication(UserCredentialsDto user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Authentication newAuth = getNew(authentication, user);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    private Authentication getNew(Authentication auth, UserCredentialsDto user) {
        UserDetails newUser = createUserDetails(user);
        return new UsernamePasswordAuthenticationToken(newUser,auth.getCredentials(),newUser.getAuthorities());
    }

    private UserDetails createUserDetails(UserCredentialsDto user) {
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(String[]::new))
                .build();
    }

}
