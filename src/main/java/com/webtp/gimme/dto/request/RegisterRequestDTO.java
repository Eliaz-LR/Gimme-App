package com.webtp.gimme.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {
    private String username;
    private String name;
    private String password;
}
