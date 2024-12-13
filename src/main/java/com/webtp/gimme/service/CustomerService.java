package com.webtp.gimme.service;

import com.webtp.gimme.dto.request.LoginRequestDTO;
import com.webtp.gimme.dto.request.RegisterRequestDTO;
import com.webtp.gimme.dto.request.UpdateProfileRequestDTO;
import com.webtp.gimme.exception.UsernameAlreadyExistsException;
import com.webtp.gimme.model.Customer;
import com.webtp.gimme.model.Offer;
import com.webtp.gimme.repository.CustomerRepository;

import java.util.List;

import com.webtp.gimme.security.CustomerDetails;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

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

    public void removeFavoriteOffer(String username, Offer offer) {
        Customer customer = this.getCustomer(username);
        if (customer != null) {
            customer.getFavoriteOffers().remove(offer);
            this.updateCustomer(customer);
        }
    }

    public boolean addFavoriteOffer(String username, Offer offer) {
        Customer customer = this.getCustomer(username);
        if (customer != null) {
            customer.getFavoriteOffers().add(offer);
            this.updateCustomer(customer);
            return true;
        }
        return false;
    }

    public List<Offer> getFavoriteOffers(String username) {
        Customer customer = this.getCustomer(username);
        if (customer != null) {
            return customer.getFavoriteOffers();
        }
        return null;
    }

    public void updateCustomer(UpdateProfileRequestDTO updateProfileRequestDTO, Customer customer) {
        if (Strings.isNotBlank(updateProfileRequestDTO.getName())) {
            customer.setName(updateProfileRequestDTO.getName());
        }
        if (Strings.isNotBlank(updateProfileRequestDTO.getPassword())) {
            customer.setPassword(passwordEncoder.encode(updateProfileRequestDTO.getPassword()));
        }
        customerRepository.save(customer);
    }

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
