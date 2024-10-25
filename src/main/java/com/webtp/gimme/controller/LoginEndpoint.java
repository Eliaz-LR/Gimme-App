package com.webtp.gimme.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginEndpoint {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        System.out.println("Tentative de connexion : username = " + username);
        return ResponseEntity.ok("Login successful");
    }
}
