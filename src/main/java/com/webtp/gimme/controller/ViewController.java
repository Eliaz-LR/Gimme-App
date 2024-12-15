package com.webtp.gimme.controller;

import com.webtp.gimme.security.CustomerDetails;
import com.webtp.gimme.service.CustomerService;
import com.webtp.gimme.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PurchaseService purchaseService;

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
        model.addAttribute("customer", customerService.getCustomer(customerDetails.getUsername()));
        return "profile-update";
    }

    @GetMapping("/profile-delete")
    public String profileDelete(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        model.addAttribute("username", customerDetails.getCustomer().getUsername());
        return "profile-delete";
    }

    @GetMapping("/profile-reception")
    public String profileReception() {
        return "profile-reception";
    }

    @GetMapping("/profile-favoris")
    public String profileFavoris(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        model.addAttribute("offers", (customerService.getFavoriteOffers(customerDetails.getUsername())));
        return "profile-favoris";
    }

    @GetMapping("/profile-historique")
    public String profileHistorique(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        model.addAttribute("purchases", (purchaseService.getPurchasesByCustomer(customerDetails.getUsername())));
        return "profile-historique";
    }

    @GetMapping("/create-offer")
    public String createOffer() {
        return "offers-create";
    }
}
