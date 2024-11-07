package com.webtp.gimme.controller;

import com.webtp.gimme.model.Customer;
import com.webtp.gimme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
public class UserEndpoint {
    
    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public Customer userGet(UUID id) {
        return userService.getUser(id);
    }

    @PostMapping("/user")
    public String userPost(@RequestBody Customer user) {
        userService.createUser(user);
        return "user created";
    }
}
