package com.webtp.gimme.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.webtp.gimme.model.Offer;
import com.webtp.gimme.model.Search;
import com.webtp.gimme.service.OfferService;

import java.util.UUID;

@Controller
@RequestMapping("/offers")
public class OfferController {
    @Autowired
    private OfferService offerService;

    @GetMapping(produces = "application/json")
    @ResponseBody
    public List<Offer> getAllOffers() {
        return offerService.getOffers();
    }

    @GetMapping(produces = "text/html")
    public String getAllOffersHtml(Model model) {
        List<Offer> offers = offerService.getOffers();
        model.addAttribute("offers", offers);
        return "offers";
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

    @GetMapping(params = "search", produces = "text/html")
    public String searchOffersHtml(@RequestParam String search, Model model, @RequestParam Optional<Offer.Category> category, @RequestParam Optional<Offer.Condition> condition, @RequestParam Optional<String> postcode, @RequestParam Optional<List<String>> keywords, @RequestParam Optional<Boolean> canBeSentByPost) {
        Search searchObj = new Search();
        searchObj.setSearchText(search);
        category.ifPresent(searchObj::setCategory);
        condition.ifPresent(searchObj::setCondition);
        postcode.ifPresent(searchObj::setPostcode);
        keywords.ifPresent(searchObj::setKeywords);
        canBeSentByPost.ifPresent(searchObj::setCanBeSentByPost);
        List<Offer> offers = offerService.searchOffers(searchObj);
        model.addAttribute("offers", offers);
        return "offers-search";
    }

    @GetMapping(params = "search", produces = "application/json")
    @ResponseBody
    public List<Offer> searchOffersJson(@RequestParam String search, @RequestParam Optional<Offer.Category> category, @RequestParam Optional<Offer.Condition> condition, @RequestParam Optional<String> postcode, @RequestParam Optional<List<String>> keywords, @RequestParam Optional<Boolean> canBeSentByPost) {
        Search searchObj = new Search();
        searchObj.setSearchText(search);
        category.ifPresent(searchObj::setCategory);
        condition.ifPresent(searchObj::setCondition);
        postcode.ifPresent(searchObj::setPostcode);
        keywords.ifPresent(searchObj::setKeywords);
        canBeSentByPost.ifPresent(searchObj::setCanBeSentByPost);
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
