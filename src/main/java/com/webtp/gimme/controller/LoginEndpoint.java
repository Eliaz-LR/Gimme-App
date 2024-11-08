package com.webtp.gimme.controller;

import com.webtp.gimme.model.Customer;
import com.webtp.gimme.model.Offer;
import com.webtp.gimme.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginEndpoint {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String username,
                                      @RequestParam String name,
                                      @RequestParam String password) {
        customerService.createUser(new Customer(username, "{noop}" + password, name));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/login");
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }
}
