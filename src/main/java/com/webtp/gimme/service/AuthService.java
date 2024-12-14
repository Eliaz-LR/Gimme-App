package com.webtp.gimme.service;

import com.webtp.gimme.dto.request.LoginRequestDTO;
import com.webtp.gimme.dto.request.RegisterRequestDTO;
import com.webtp.gimme.exception.UsernameAlreadyExistsException;
import com.webtp.gimme.model.Customer;
import com.webtp.gimme.repository.CustomerRepository;
import com.webtp.gimme.security.CustomerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Customer registerCustomer(RegisterRequestDTO registerRequestDTO) {
        if (customerRepository.existsById(registerRequestDTO.getUsername())) {
            throw new UsernameAlreadyExistsException("Ce nom d'utilisateur est déjà pris.");
        }
        Customer customer = new Customer();
        customer.setUsername(registerRequestDTO.getUsername());
        customer.setName(registerRequestDTO.getName());
        customer.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        return customerRepository.save(customer);
    }

    public CustomerDetails logCustomer(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
        );
        return (CustomerDetails) authentication.getPrincipal();
    }
}
