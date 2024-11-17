package com.webtp.gimme.service;

import com.webtp.gimme.model.Offer;
import com.webtp.gimme.model.Search;
import com.webtp.gimme.model.Offer.Category;
import com.webtp.gimme.model.Offer.Condition;
import com.webtp.gimme.repository.OfferRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    public List<Offer> getOffers() {
        return offerRepository.findAll();
    }

    public Offer getOfferByID(UUID id) {
        return offerRepository.findById(id).orElse(null);
    }

    public List<Offer> getOffersByName(String name) {
        return offerRepository.findByName(name);
    }

    public List<Offer> getOffersByCategory(Offer.Category category) {
        return offerRepository.findByCategory(category);
    }

    public void createOffer(Offer offer) {
        offerRepository.save(offer);
    }

    public boolean deleteOfferByID(UUID id) {
        Optional<Offer> offer = offerRepository.findById(id);
        if (offer.isPresent()) {
            offerRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateOffer(Offer offer) {
        Optional<Offer> existingOffer = offerRepository.findById(offer.getId());
        if (existingOffer.isPresent()) {
            offerRepository.save(offer);
            return true;
        } else {
            return false;
        }
    }

    public List<Offer> searchOffers(Search search) {
                return offerRepository.searchOffersByNameOrDescription(
                        search.getSearchText(),
                        search.getCategory(),
                        search.getCondition(),
                        search.getPostcode(),
                        search.getKeywords(),
                        search.getCanBeSentByPost());
    }
}
