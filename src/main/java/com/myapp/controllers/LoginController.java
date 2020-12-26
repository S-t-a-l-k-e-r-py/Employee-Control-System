package com.myapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String showMyLoginPage() {
        return "registration-pages/login-page";
    }
    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";

    }

}
