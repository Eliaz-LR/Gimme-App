package com.webtp.gimme.controller;

import com.webtp.gimme.dto.OfferSearchDto;
import com.webtp.gimme.model.Customer;
import com.webtp.gimme.model.Search;
import com.webtp.gimme.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/searches")
public class SearchController {
    
    @Autowired
    private SearchService searchService;

    @PostMapping
    public ResponseEntity<Search> saveSearch(@RequestBody OfferSearchDto offerSearchDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = (Customer) authentication.getPrincipal();

        Search search = new Search(offerSearchDto);
        search.setCustomer(customer);

        Search savedSearch = searchService.saveSearch(search);
        return new ResponseEntity<>(savedSearch, HttpStatus.CREATED);
    }
}
