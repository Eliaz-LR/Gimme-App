package com.webtp.gimme.security;

import com.webtp.gimme.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PasswordHashingInitializer implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public PasswordHashingInitializer(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        customerRepository.findAll().forEach(customer -> {
            String plainPassword = customer.getPassword();

            if (!plainPassword.startsWith("$2a$")) {
                customer.setPassword(passwordEncoder.encode(plainPassword));
                customerRepository.save(customer);
            }
        });
    }
}
