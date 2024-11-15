package com.webtp.gimme.service;

import com.webtp.gimme.dto.request.RegisterRequestDTO;
import com.webtp.gimme.exception.UsernameAlreadyExistsException;
import com.webtp.gimme.model.Customer;
import com.webtp.gimme.repository.CustomerRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(String username) {
        return customerRepository.findById(username).orElse(null);
    }

    public int createCustomer(Customer user) {
        if (customerRepository.findById(user.getUsername()).isPresent()) {
            return 409;
        }
        customerRepository.save(user);
        return 200;
    }

    public void deleteCustomer(String username) {
        customerRepository.deleteById(username);
    }

    public void updateCustomer(Customer user) {
        customerRepository.save(user);
    }

    public void registerCustomer(RegisterRequestDTO registerRequestDTO) {
        if (customerRepository.existsById(registerRequestDTO.getUsername())) {
            throw new UsernameAlreadyExistsException("Nom d'utilisateur déjà pris");
        }

        Customer customer = new Customer();
        customer.setUsername(registerRequestDTO.getUsername());
        customer.setName(registerRequestDTO.getName());
        customer.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        customerRepository.save(customer);
    }
}
