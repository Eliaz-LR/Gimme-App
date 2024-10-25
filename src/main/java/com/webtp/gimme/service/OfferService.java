package com.webtp.gimme.service;

import com.webtp.gimme.model.Offer;
import com.webtp.gimme.repository.OfferRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    public List<Offer> getOffers() {
        return offerRepository.findAll();
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

    public boolean deleteOfferByID(Long id) {
        Optional<Offer> offer = offerRepository.findById(id);
        if (offer.isPresent()) {
            offerRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
