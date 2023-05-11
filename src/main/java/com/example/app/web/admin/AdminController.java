package com.example.app.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    public static final String NOTIFICATION_ATTRIBUTE = "notification";

    @GetMapping("/admin")
    public String getAdminPanel(Model model) {
        String jumbotronHeader = "Admin Panel";
        String jumbotronDescription = "The secret place for editors and admins where they run the whole MI Tube world!";
        model.addAttribute("jumbotronHeader", jumbotronHeader);
        model.addAttribute("jumbotronDescription", jumbotronDescription);
        return "layout-elements/content/admin/admin";
    }
}
