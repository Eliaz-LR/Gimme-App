package com.webtp.gimme.controller;

import com.webtp.gimme.model.Customer;
import com.webtp.gimme.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<String> userPost(@RequestBody Customer user) {
        customerService.createUser(user);
        return ResponseEntity.created(URI.create("/user/" + user.getId())).build();
    }

    @DeleteMapping("/user/{id}")
    public String userDelete(UUID id) {
        customerService.deleteUser(id);
        return "user deleted";
    }

    @PutMapping("/user/{id}")
    public String userPut(@RequestBody Customer user) {
        customerService.updateUser(user);
        return "user updated";
    }
}
