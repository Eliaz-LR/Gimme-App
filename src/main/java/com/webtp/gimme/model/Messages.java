package com.webtp.gimme.model;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@Getter
@Setter
public class Messages {
    @Id
    @GeneratedValue
    private UUID id;

    private String message;
    private String sender;
    private Timestamp timestamp;
}
