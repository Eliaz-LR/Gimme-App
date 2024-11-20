package com.webtp.gimme.security;

import com.webtp.gimme.model.Customer;
import com.webtp.gimme.repository.CustomerRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CustomerDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final Map<String, UserDetails> cache = new ConcurrentHashMap<>();

    public CustomerDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return cache.computeIfAbsent(username, key -> {
            Customer customer = customerRepository.findById(username).orElse(null);
            if (customer == null) {
                throw new UsernameNotFoundException("Invalid username or password");
            }
            return new CustomerDetails(customer);
        });
    }

    public void evictCache(String username) {
        cache.remove(username);
    }
}

