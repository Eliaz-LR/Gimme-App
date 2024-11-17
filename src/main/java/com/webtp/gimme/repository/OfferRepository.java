package com.webtp.gimme.repository;

import com.webtp.gimme.model.Offer;
import com.webtp.gimme.model.Search;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {
    List<Offer> findByName(String name);

    List<Offer> findByCategory(Offer.Category category);

    @Query("""
        SELECT o FROM Offer o 
        WHERE (LOWER(o.name) LIKE LOWER(CONCAT('%', :nameOrDescription, '%')) 
            OR LOWER(o.description) LIKE LOWER(CONCAT('%', :nameOrDescription, '%')))
        AND (:category IS NULL OR o.category = :category)
        AND (:condition IS NULL OR o.condition = :condition)
        AND (:postcode IS NULL OR o.postcode = :postcode)
        AND (:keywords IS NULL OR o.keywords IN :keywords)
        AND (:canBeSentByPost IS NULL OR o.canBeSentByPost = :canBeSentByPost)
        ORDER BY o.name
        """)
    List<Offer> searchOffersByNameOrDescription(
            @Param("nameOrDescription") String nameOrDescription,
            @Param("category") Offer.Category category,
            @Param("condition") Offer.Condition condition,
            @Param("postcode") String postcode,
            @Param("keywords") List<String> keywords,
            @Param("canBeSentByPost") Boolean canBeSentByPost);
}
