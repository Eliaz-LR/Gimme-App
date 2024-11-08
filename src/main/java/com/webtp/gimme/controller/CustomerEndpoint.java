package com.webtp.gimme.controller;

import com.webtp.gimme.model.Customer;
import com.webtp.gimme.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class CustomerEndpoint {
    
    @Autowired
    private CustomerService customerService;

    @GetMapping("/user/{username}")
    public ResponseEntity<Customer> userGet(@PathVariable String username) {
        Customer user = customerService.getUser(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    public List<Customer> usersGet() {
        return customerService.getUsers();
    }

    @PostMapping("/user")
    public ResponseEntity<String> userPost(@RequestBody Customer user) {
        int status = customerService.createUser(user);
        if (status == 409) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        return ResponseEntity.created(URI.create("/user/" + user.getUsername())).build();
    }

    @DeleteMapping("/user/{username}")
    public String userDelete(@PathVariable String username) {
        customerService.deleteUser(username);
        return "user deleted";
    }

    @PutMapping("/user/{username}")
    public String userPut(@RequestBody Customer user) {
        customerService.updateUser(user);
        return "user updated";
    }
}
