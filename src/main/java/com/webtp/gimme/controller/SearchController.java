package com.webtp.gimme.controller;

import com.webtp.gimme.dto.request.OfferSearchRequestDto;
import com.webtp.gimme.model.Customer;
import com.webtp.gimme.model.Search;
import com.webtp.gimme.security.CustomerDetails;
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
    public ResponseEntity<Search> saveSearch(@ModelAttribute OfferSearchRequestDto offerSearchRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        Customer customer = customerDetails.getCustomer();

        Search search = new Search(offerSearchRequestDto);
        search.setCustomer(customer);

        Search savedSearch = searchService.saveSearch(search);
        return new ResponseEntity<>(savedSearch, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<Search>> getSearches() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        Customer customer = customerDetails.getCustomer();

        Iterable<Search> searches = searchService.getSearches(customer);
        return new ResponseEntity<>(searches, HttpStatus.OK);
    }

    @DeleteMapping("/{searchId}")
    public ResponseEntity<Void> deleteSearch(@PathVariable String searchId) {
        Search search = searchService.getSearchById(searchId);

        searchService.deleteSearch(search);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
