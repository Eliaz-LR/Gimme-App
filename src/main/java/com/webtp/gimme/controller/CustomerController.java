package com.webtp.gimme.controller;

import com.webtp.gimme.model.Customer;
import com.webtp.gimme.model.Offer;
import com.webtp.gimme.service.CustomerService;
import com.webtp.gimme.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

import java.net.URI;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;


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

    @DeleteMapping("/users/{username}")
    public String userDelete(@PathVariable String username) {
        customerService.deleteCustomer(username);
        return "user deleted";
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
}
