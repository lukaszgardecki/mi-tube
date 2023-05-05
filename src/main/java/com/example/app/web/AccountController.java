package com.example.app.web;

import com.example.app.domain.user.UserService;
import com.example.app.domain.user.dto.UserManageAccountDto;
import com.example.app.domain.user.dto.UserSessionDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {
    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/myaccount")
    public String getAccountSettings(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserManageAccountDto userData = userService.findUserDataByEmail(username).orElseThrow();
        model.addAttribute("userData", userData);
        return "layout-elements/content/manage-account";
    }

    @PostMapping("/myaccount")
    public String saveAccount(UserManageAccountDto user, HttpSession session) {
        UserSessionDto updatedUser = userService.updateUserData(user);
        session.setAttribute("user", updatedUser);
        return "redirect:/myaccount";
    }
}
