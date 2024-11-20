package com.webtp.gimme.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Search {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Customer customer;

    private String searchText;
    
    private Offer.Category category = null;

    private Offer.Condition condition = null;

    private String postcode = null;

    private List<String> keywords = null;

    private Boolean canBeSentByPost = null;
}