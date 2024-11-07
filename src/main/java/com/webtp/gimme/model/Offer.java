package com.webtp.gimme.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Offer {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private Category category = Category.OTHER;

    public enum Category {
        ELECTRONICS, FASHION, HOME, OTHER
    }

    @Enumerated(EnumType.STRING)
    private Condition condition = Condition.GOOD;

    public enum Condition {
        NEW_WITH_TAG, NEW_WITHOUT_TAG, VERY_GOOD, GOOD, ACCEPTABLE, POOR
    }

    private Date postedDate = new Date(System.currentTimeMillis());

    private String postcode;

    private List<String> keywords = List.of();

    private Boolean canBeSentByPost = false;

    private Boolean isActive = true;
}
