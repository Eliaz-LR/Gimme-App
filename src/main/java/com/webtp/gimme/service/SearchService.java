package com.webtp.gimme.service;

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
}
