package com.webtp.gimme.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.CascadeType;


@Entity
@Getter
@Setter
public class Chat {
    @Id
    @GeneratedValue
    private UUID id;

    private List<String> username;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Messages> messages;

    public boolean addMessage(Messages message) {
        return this.messages.add(message);
    }
}
