package com.webtp.gimme.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webtp.gimme.model.Customer;
import com.webtp.gimme.model.Search;
import com.webtp.gimme.repository.CustomerRepository;
import com.webtp.gimme.repository.SearchRepository;

@Service
public class SearchService {

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Search saveSearch(Search search) {
        searchRepository.save(search);
        search.getCustomer().getSavedSearches().add(search);
        customerRepository.save(search.getCustomer());
        return search;
    }
    
    public void deleteSearch(Search search) {
        Customer customer = search.getCustomer();
        customer.getSavedSearches().remove(search);
        customerRepository.save(customer);
        searchRepository.delete(search);
    }

    public Iterable<Search> getSearches(Customer customer) {
        return searchRepository.findByCustomer(customer);
    }

    public Search getSearchById(String searchId) {
        UUID searchIdUUID = UUID.fromString(searchId);
        return searchRepository.findById(searchIdUUID).orElse(null);
    }
}
