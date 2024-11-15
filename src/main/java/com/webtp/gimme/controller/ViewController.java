package com.webtp.gimme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/profile-update")
    public String profileUpdate() {
        return "profile-update";
    }

    @GetMapping("/profile-delete")
    public String profileDelete() {
        return "profile-delete";
    }
}
