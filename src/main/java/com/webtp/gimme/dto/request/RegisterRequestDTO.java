package com.webtp.gimme.dto.request;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class RegisterRequestDTO {

    @NotBlank(message = "Le nom d'utilisateur est requis et ne peut pas être vide ou composé uniquement d'espaces.")
    private String username;

    @NotBlank(message = "Le prénom est requis et ne peut pas être vide ou composé uniquement d'espaces.")
    private String name;

    @NotBlank(message = "Le mot de passe est requis et ne peut pas être vide ou composé uniquement d'espaces.")
    private String password;
}
