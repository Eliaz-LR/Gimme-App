package com.webtp.gimme.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webtp.gimme.dto.request.OfferSearchRequestDto;

@Entity
@Getter
@Setter
public class Search {

    public Search() {
    }

    public Search(OfferSearchRequestDto offerSearchRequestDto) {
        this.searchText = offerSearchRequestDto.getSearch();
        offerSearchRequestDto.getCategory().ifPresent(this::setCategory);
        offerSearchRequestDto.getCondition().ifPresent(this::setCondition);
        offerSearchRequestDto.getPostcode().ifPresent(this::setPostcode);
        offerSearchRequestDto.getKeywords().ifPresent(this::setKeywords);
        offerSearchRequestDto.getCanBeSentByPost().ifPresent(this::setCanBeSentByPost);
    }

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JsonIgnore
    private Customer customer;

    private String searchText;
    
    private Offer.Category category = null;

    private Offer.Condition condition = null;

    private String postcode = null;

    private List<String> keywords = null;

    private Boolean canBeSentByPost = null;
}