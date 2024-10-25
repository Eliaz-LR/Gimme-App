package com.webtp.gimme.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.webtp.gimme.model.Offer;
import com.webtp.gimme.service.OfferService;

@RestController
@RequestMapping("/offers")
public class OfferEndpoint {
    @Autowired
    private OfferService offerService;

    @GetMapping
    public List<Offer> getAllOffers() {
        return offerService.getOffers();
    }

    @GetMapping(params = "search")
    public List<Offer> searchOffer(@RequestParam(value = "search") String search) {
        return offerService.getOffersByName(search);
    }

    @GetMapping(params = "category")
    public List<Offer> getOfferByCategory(@RequestParam(value = "category") Offer.Category category) {
        return offerService.getOffersByCategory(category);
    }

    @PostMapping
    public String offersPost(@RequestBody Offer offer) {
        offerService.createOffer(offer);
        return "Offre créée";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOffer(@PathVariable Long id) {
        boolean deleted = offerService.deleteOfferByID(id);
        if (deleted) {
            return ResponseEntity.ok("Offer deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Offer not found");
        }

    }
}
