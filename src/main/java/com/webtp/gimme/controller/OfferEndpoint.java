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

}
