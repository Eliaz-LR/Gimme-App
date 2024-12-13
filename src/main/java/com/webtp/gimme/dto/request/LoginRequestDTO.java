package com.webtp.gimme.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    @NotBlank(message = "Le nom d'utilisateur est requis et ne peut pas être vide ou composé uniquement d'espaces.")
    private String username;

    @NotBlank(message = "Le mot de passe est requis et ne peut pas être vide ou composé uniquement d'espaces.")
    private String password;
}
