package com.webtp.gimme.service;

import com.webtp.gimme.exception.RessourceNotFoundException;
import com.webtp.gimme.model.Customer;
import com.webtp.gimme.model.Offer;
import com.webtp.gimme.model.Purchase;
import com.webtp.gimme.repository.CustomerRepository;
import com.webtp.gimme.repository.OfferRepository;
import com.webtp.gimme.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PurchaseService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Transactional
    public Purchase purchaseOffer(String username, UUID offerId) throws IllegalArgumentException {
        Customer customer = customerRepository.getCustomerByUsername(username);
        Offer offer = offerRepository.findById(offerId).orElse(null);
        if (offer == null || !offer.getIsActive()) {
            throw new RessourceNotFoundException("Cette offre n'existe pas.");
        }

        Purchase purchase = new Purchase();
        purchase.setCustomer(customer);
        purchase.setOffer(offer);
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase = purchaseRepository.save(purchase);

        offer.setIsActive(false);
        offerRepository.save(offer);

        return purchase;
    }

    public List<Purchase> getPurchasesByCustomer(String username) {
        return purchaseRepository.findByCustomerUsername(username);
    }
}
