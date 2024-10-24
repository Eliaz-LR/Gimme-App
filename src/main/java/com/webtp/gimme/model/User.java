package com.webtp.gimme.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Getter
@Setter
public class User {
    
    @Id
    @GeneratedValue
    private UUID id;

    private String username;

    private String password;

    private String name;

    private String surname;

    private String email;
    
    private String phone;

    private String dateOfBirth;

    private String address;

    private String description;
}
