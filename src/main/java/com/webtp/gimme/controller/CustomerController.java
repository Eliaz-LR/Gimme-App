package com.webtp.gimme.controller;

import com.webtp.gimme.dto.request.UpdateProfileRequestDTO;
import com.webtp.gimme.model.Customer;
import com.webtp.gimme.model.Offer;
import com.webtp.gimme.service.CustomerService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

@RestController
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers/{username}")
    public ResponseEntity<?> customerGet(@PathVariable String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer user = customerService.getCustomer(username, authentication);
        return ResponseEntity.ok(user);
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

    @PutMapping("/customers/{username}")
    public ResponseEntity<?> customerPut(@PathVariable String username, @RequestBody UpdateProfileRequestDTO updateProfileRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.updateCustomer(updateProfileRequestDTO, username, authentication);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/customers/{username}/favoriteOffers")
    public ResponseEntity<?> customerGetFavoriteOffers(@PathVariable String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Offer> favoriteOffers = customerService.getFavoriteOffers(username, authentication);
        if (favoriteOffers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(favoriteOffers);
    }

    @PutMapping("/customers/{username}/favoriteOffers/{offerId}")
    public ResponseEntity<?> customerPutFavoriteOffer(@PathVariable String username, @PathVariable UUID offerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.addFavoriteOffer(username, offerId, authentication);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/customers/{username}/favoriteOffers/{offerId}")
    public ResponseEntity<?> customerDeleteFavoriteOffer(@PathVariable String username, @PathVariable String offerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.removeFavoriteOffer(username, UUID.fromString(offerId), authentication);
        return ResponseEntity.ok(customer);
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
