package com.webtp.gimme.controller;

import com.webtp.gimme.dto.request.UpdateProfileRequestDTO;
import com.webtp.gimme.model.Customer;
import com.webtp.gimme.model.Offer;
import com.webtp.gimme.service.CustomerService;
import com.webtp.gimme.utils.SecurityUtils;
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
@RequestMapping("/customers/{username}")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<?> customerGet(@PathVariable String username) {
        SecurityUtils.checkAuthorization(username);
        Customer user = customerService.getCustomer(username);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping
    public ResponseEntity<?> customerDelete(@PathVariable String username, HttpServletRequest request, HttpServletResponse response) {
        SecurityUtils.checkAuthorization(username);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        customerService.deleteCustomer(username);
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        SecurityUtils.deleteCookie(response);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping
    public ResponseEntity<?> customerPut(@PathVariable String username, @RequestBody UpdateProfileRequestDTO updateProfileRequestDTO) {
        SecurityUtils.checkAuthorization(username);
        Customer customer = customerService.updateCustomer(updateProfileRequestDTO, username);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/favoriteOffers")
    public ResponseEntity<?> customerGetFavoriteOffers(@PathVariable String username) {
        SecurityUtils.checkAuthorization(username);
        List<Offer> favoriteOffers = customerService.getFavoriteOffers(username);
        if (favoriteOffers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(favoriteOffers);
    }

    @PutMapping("/favoriteOffers/{offerId}")
    public ResponseEntity<?> customerPutFavoriteOffer(@PathVariable String username, @PathVariable UUID offerId) {
        SecurityUtils.checkAuthorization(username);
        Customer customer = customerService.addFavoriteOffer(username, offerId);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/favoriteOffers/{offerId}")
    public ResponseEntity<?> customerDeleteFavoriteOffer(@PathVariable String username, @PathVariable String offerId) {
        SecurityUtils.checkAuthorization(username);
        Customer customer = customerService.removeFavoriteOffer(username, UUID.fromString(offerId));
        return ResponseEntity.ok(customer);
    }
}
