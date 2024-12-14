package com.webtp.gimme.service;

import com.webtp.gimme.dto.request.UpdateProfileRequestDTO;
import com.webtp.gimme.exception.BodyNotValidException;
import com.webtp.gimme.exception.RessourceNotFoundException;
import com.webtp.gimme.model.Customer;
import com.webtp.gimme.model.Offer;
import com.webtp.gimme.repository.CustomerRepository;

import com.webtp.gimme.repository.OfferRepository;
import com.webtp.gimme.security.CustomerDetails;
import com.webtp.gimme.security.CustomerDetailsService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerDetailsService customerDetailsService;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Customer getCustomer(String username, Authentication authentication) {
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        if (!customerDetails.getCustomer().getUsername().equals(username)) {
            throw new AccessDeniedException("Vous n'avez pas les droits nécessaires pour récupérer cet utilisateur.");
        }
        return customerRepository.getCustomerByUsername(username);
    }

    public Customer updateCustomer(UpdateProfileRequestDTO updateProfileRequestDTO, String username, Authentication authentication) {
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        if (!customerDetails.getCustomer().getUsername().equals(username)) {
            throw new AccessDeniedException("Vous n'avez pas les droits nécessaires pour modifier cet utilisateur.");
        }
        Customer customer = customerRepository.getCustomerByUsername(username);
        if (Strings.isNotBlank(updateProfileRequestDTO.getName())) {
            customer.setName(updateProfileRequestDTO.getName());
        }
        if (Strings.isNotBlank(updateProfileRequestDTO.getPassword())) {
            customer.setPassword(passwordEncoder.encode(updateProfileRequestDTO.getPassword()));
        }
        customerDetailsService.evictCache(username);
        return customerRepository.save(customer);
    }

    public void deleteCustomer(String username, Authentication authentication) {
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        if (!customerDetails.getCustomer().getUsername().equals(username)) {
            throw new AccessDeniedException("Vous n'avez pas les droits nécessaires pour supprimer cet utilisateur.");
        }
        customerRepository.deleteById(username);
        customerDetailsService.evictCache(username);
    }

    public List<Offer> getFavoriteOffers(String username, Authentication authentication) {
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        if (!customerDetails.getCustomer().getUsername().equals(username)) {
            throw new AccessDeniedException("Vous n'avez pas les droits nécessaires.");
        }
        return customerRepository.getCustomerByUsername(username).getFavoriteOffers();
    }

    public Customer addFavoriteOffer(String username, UUID offerId, Authentication authentication) {
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        if (!customerDetails.getCustomer().getUsername().equals(username)) {
            throw new AccessDeniedException("Vous n'avez pas les droits nécessaires.");
        }
        Customer customer = customerRepository.getCustomerByUsername(username);
        Offer offer = offerRepository.findById(offerId).orElse(null);
        if (offer == null) {
            throw new RessourceNotFoundException("Cette offre n'existe pas.");
        }
        for (Offer favorite : customer.getFavoriteOffers()) {
            if (favorite.getId().equals(offerId)) {
                throw new BodyNotValidException("Cette offre est déjà dans la liste des favoris.");
            }
        }
        customer.getFavoriteOffers().add(offer);
        return customerRepository.save(customer);
    }

    public Customer removeFavoriteOffer(String username, UUID offerId, Authentication authentication) {
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        if (!customerDetails.getCustomer().getUsername().equals(username)) {
            throw new AccessDeniedException("Vous n'avez pas les droits nécessaires.");
        }
        Customer customer = customerRepository.getCustomerByUsername(username);
        Offer offer = offerRepository.findById(offerId).orElse(null);
        if (offer == null) {
            throw new RessourceNotFoundException("Cette offre n'existe pas.");
        }
        for (Offer favorite : customer.getFavoriteOffers()) {
            if (favorite.getId().equals(offerId)) {
                customer.getFavoriteOffers().remove(favorite);
                return customerRepository.save(customer);
            }
        }
        return customer;
    }
}
