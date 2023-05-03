package com.example.app.web;

import com.example.app.domain.user.UserService;
import com.example.app.domain.user.dto.UserSessionDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login-form";
    }

    @GetMapping("/login-success")
    public String loginSuccess(HttpSession session, Authentication authentication) {
        String username = authentication.getName();
        UserSessionDto user = userService.findUserByEmail(username).orElseThrow();
        session.setAttribute("user", user);
        return "redirect:/";
    }
}
