package com.webtp.gimme.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.webtp.gimme.dto.request.OfferSearchRequestDto;
import com.webtp.gimme.model.Offer;
import com.webtp.gimme.model.Search;
import com.webtp.gimme.service.OfferService;

import java.util.UUID;

@RestController
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @GetMapping(produces = "application/json")
    @ResponseBody
    public List<Offer> getAllOffers() {
        return offerService.getOffers();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Offer> getOffer(@PathVariable UUID id) {
        Offer offer = offerService.getOfferByID(id);
        if (offer != null) {
            return ResponseEntity.ok(offer);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping(params = "search", produces = "application/json")
    @ResponseBody
    public List<Offer> searchOffersJson(@ModelAttribute OfferSearchRequestDto offerSearchRequestDto) {
        Search searchObj = new Search(offerSearchRequestDto);
        return offerService.searchOffers(searchObj);
    }

    @GetMapping(params = "name")
    @ResponseBody
    public List<Offer> getOffersByName(@RequestParam(value = "name") String name) {
        return offerService.getOffersByName(name);
    }

    @GetMapping(params = "category")
    @ResponseBody
    public List<Offer> getOfferByCategory(@RequestParam(value = "category") Offer.Category category) {
        return offerService.getOffersByCategory(category);
    }

    @PostMapping
    public ResponseEntity<String> offersPost(@RequestBody Offer offer) {
        offerService.createOffer(offer);
        return ResponseEntity.created(URI.create("/offers/" + offer.getId())).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOffer(@PathVariable UUID id) {
        boolean deleted = offerService.deleteOfferByID(id);
        if (deleted) {
            return ResponseEntity.ok("Offer deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Offer not found");
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> updateOffer(@PathVariable String id, @RequestBody Offer offer) {
        offer.setId(UUID.fromString(id));
        boolean updated = offerService.updateOffer(offer);
        if (updated) {
            return ResponseEntity.ok("Offer updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Offer not found");
        }
    }
}
