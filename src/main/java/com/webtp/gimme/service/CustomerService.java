package com.webtp.gimme.service;

import com.webtp.gimme.model.Customer;
import com.webtp.gimme.repository.CustomerRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getUsers() {
        return customerRepository.findAll();
    }

    public Customer getUser(String username) {
        Customer customer = customerRepository.findById(username).orElse(null);
        return customer;
    }

    public int createUser(Customer user) {
        if (customerRepository.findById(user.getUsername()).isPresent()) {
            return 409;
        }
        customerRepository.save(user);
        return 200;
    }

    public void deleteUser(String username) {
        customerRepository.deleteById(username);
    }

    public void updateUser(Customer user) {
        customerRepository.save(user);
    }
}
