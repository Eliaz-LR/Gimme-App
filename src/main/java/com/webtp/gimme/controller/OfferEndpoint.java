package com.webtp.gimme.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.webtp.gimme.model.Offer;
import com.webtp.gimme.service.OfferService;

@RestController
public class OfferEndpoint {
    @Autowired
    private OfferService offerService;

    @GetMapping("/offers")
    public List<Offer> offers() {
        return offerService.getOffers();
    }

    // @GetMapping("/offers")
    // public List<Offer> offers(@RequestParam(value = "search") String search) {
    // return offerService.getOffersByName(search);
    // }

    @PostMapping("/offers")
    public String offersPost(@RequestBody Offer offer) {
        offerService.createOffer(offer);
        return "Offre créée";
    }
}
