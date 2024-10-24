package com.webtp.gimme.service;

import com.webtp.gimme.model.Offer;
import com.webtp.gimme.repository.OfferRepository;
import java.util.List;
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

    public void createOffer(Offer offer) {
        offerRepository.save(offer);
    }
}
