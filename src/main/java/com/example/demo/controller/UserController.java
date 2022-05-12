package com.example.demo.controller;

import com.example.demo.models.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/user")
    public String showUserInformation(@AuthenticationPrincipal User user, ModelMap model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles());
        return "user_page";
    }
}
