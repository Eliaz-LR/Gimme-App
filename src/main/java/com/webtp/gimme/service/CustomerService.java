package com.webtp.gimme.service;

import com.webtp.gimme.model.Customer;
import com.webtp.gimme.repository.CustomerRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer getUser(UUID id) {
        return customerRepository.findById(id).orElse(new Customer());
    }

    public void createUser(Customer user) {
        customerRepository.save(user);
    }

    public void deleteUser(UUID id) {
        customerRepository.deleteById(id);
    }

    public void updateUser(Customer user) {
        customerRepository.save(user);
    }
}
