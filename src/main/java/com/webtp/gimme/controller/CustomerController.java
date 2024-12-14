package com.webtp.gimme.controller;

import com.webtp.gimme.dto.request.UpdateProfileRequestDTO;
import com.webtp.gimme.dto.response.RegisterResponseDTO;
import com.webtp.gimme.model.Customer;
import com.webtp.gimme.model.Offer;
import com.webtp.gimme.security.CustomerDetails;
import com.webtp.gimme.service.CustomerService;
import com.webtp.gimme.service.OfferService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

import java.net.URI;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;

    private OfferService offerService;

    @GetMapping("/users/{username}")
    public ResponseEntity<Customer> userGet(@PathVariable String username) {
        Customer user = customerService.getCustomer(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    public List<Customer> usersGet() {
        return customerService.getCustomers();
    }

    @PostMapping("/users")
    public ResponseEntity<String> userPost(@RequestBody Customer user) {
        int status = customerService.createCustomer(user);
        if (status == 409) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        return ResponseEntity.created(URI.create("/user/" + user.getUsername())).build();
    }

    @DeleteMapping("/customers/{username}")
    public ResponseEntity<?> customerDelete(@PathVariable String username, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        customerService.deleteCustomer(username, authentication);
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        deleteCookie(response);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/users/{username}")
    public String userPut(@RequestBody Customer user) {
        customerService.updateCustomer(user);
        return "user updated";
    }

    @DeleteMapping("/users/{username}/favoriteOffer/{offerId}") 
    public String favoriteOfferDelete(@PathVariable String username, @PathVariable UUID offerId) {
        Offer offer = offerService.getOfferByID(offerId);
        customerService.removeFavoriteOffer(username, offer);
        return "favorite offer removed";
    }

    @GetMapping("/users/{username}/favoriteOffers")
    public List<Offer> getFavoriteOffers(@PathVariable String username) {
        return customerService.getFavoriteOffers(username);
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

    private void deleteCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("JWT", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
