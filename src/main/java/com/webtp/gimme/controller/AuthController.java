package com.webtp.gimme.controller;

import com.webtp.gimme.dto.request.RegisterRequestDTO;
import com.webtp.gimme.dto.request.UpdateProfileRequestDTO;
import com.webtp.gimme.security.CustomerDetails;
import com.webtp.gimme.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@ModelAttribute RegisterRequestDTO registerDTO, RedirectAttributes redirectAttributes) {
        customerService.registerCustomer(registerDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Inscription réussie ! Vous pouvez maintenant vous connecter.");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/login");
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }

    @PostMapping("/customer")
    public ResponseEntity<?> updateProfile(@ModelAttribute UpdateProfileRequestDTO updateProfileRequestDTO, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        customerService.updateCustomer(updateProfileRequestDTO, customerDetails.getCustomer());
        redirectAttributes.addFlashAttribute("successMessage", "Profil mis à jour avec succès");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/profile-update");
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }

    @PostMapping("/profile-delete")
    public ResponseEntity<?> deleteProfile(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        customerService.deleteCustomer(customerDetails.getCustomer().getUsername());
        new SecurityContextLogoutHandler().logout(request, response, authentication);

        redirectAttributes.addFlashAttribute("successMessage", "Votre compte a été supprimé avec succès.");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/login");
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }
}
