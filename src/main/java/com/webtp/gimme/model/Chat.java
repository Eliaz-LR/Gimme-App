package com.webtp.gimme.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Chat {
    @Id
    @GeneratedValue
    private UUID id;

    private String username;

    private String usernameOfRecipient;


    @OneToMany(fetch = FetchType.EAGER)
    private List<Messages> messages;

    public void addMessage(Messages message) {
        this.messages.add(message);
    }
}
