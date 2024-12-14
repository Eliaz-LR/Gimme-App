package com.webtp.gimme.controller;

import com.webtp.gimme.dto.request.LoginRequestDTO;
import com.webtp.gimme.dto.request.RegisterRequestDTO;
import com.webtp.gimme.dto.response.LoginResponseDTO;
import com.webtp.gimme.dto.response.RegisterResponseDTO;
import com.webtp.gimme.model.Customer;
import com.webtp.gimme.security.CustomerDetails;
import com.webtp.gimme.security.jwt.JwtService;
import com.webtp.gimme.service.AuthService;
import com.webtp.gimme.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        Customer customer = authService.registerCustomer(registerRequestDTO);
        String location = "/customers/" + customer.getUsername();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, location)
                .body(new RegisterResponseDTO("Utilisateur enregistré avec succès.", customer.getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        CustomerDetails customerDetails = authService.logCustomer(loginRequestDTO);
        String jwt = jwtService.generateToken(customerDetails);
        SecurityUtils.setCookie(response, jwt);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new LoginResponseDTO("Authentification réussie", jwt));
    }
}
