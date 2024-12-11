package com.webtp.gimme.controller;

import com.webtp.gimme.security.CustomerDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String profileUpdate(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        model.addAttribute("customer", customerDetails.getCustomer());
        return "profile-update";
    }

    @GetMapping("/profile-delete")
    public String profileDelete() {
        return "profile-delete";
    }

    @GetMapping("/profile-favoris")
    public String profileFavoris() {
        return "profile-favoris";
    }

    @GetMapping("/profile-reception")
    public String profileReception() {
        return "profile-reception";
    }

    @GetMapping("/create-offer")
    public String createOffer() {
        return "create-offer";
    }
}
