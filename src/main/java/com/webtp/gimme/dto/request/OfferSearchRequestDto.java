package com.webtp.gimme.dto.request;

import lombok.Data;
import com.webtp.gimme.model.Offer;

import java.util.List;
import java.util.Optional;

@Data
public class OfferSearchRequestDto {
    private String search;

    private Optional<Offer.Category> category = Optional.empty();
    private Optional<Offer.Condition> condition = Optional.empty();
    private Optional<String> postcode = Optional.empty();
    private Optional<List<String>> keywords = Optional.empty();
    private Optional<Boolean> canBeSentByPost = Optional.empty();
}
