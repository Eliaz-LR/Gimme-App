package com.webtp.gimme.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    private String username;

    private String password;

    private String name;

    @OneToMany(mappedBy = "customer")
    private List<Search> savedSearches;

    @OneToMany
    private List<Offer> favoriteOffers;
}
