package com.webtp.gimme.controller;

import com.webtp.gimme.model.Customer;
import com.webtp.gimme.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
public class CustomerEndpoint {
    
    @Autowired
    private CustomerService customerService;

    @GetMapping("/user/{id}")
    public Customer userGet(UUID id) {
        return customerService.getUser(id);
    }

    @PostMapping("/user")
    public String userPost(@RequestBody Customer user) {
        customerService.createUser(user);
        return "user created";
    }
}
