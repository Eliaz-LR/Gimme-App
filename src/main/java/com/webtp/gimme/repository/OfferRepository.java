package com.webtp.gimme.repository;

import com.webtp.gimme.model.Offer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {
    List<Offer> findByName(String name);

    List<Offer> findByCategory(Offer.Category category);
}
