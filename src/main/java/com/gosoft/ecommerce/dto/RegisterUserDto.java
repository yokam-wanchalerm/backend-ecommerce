package com.gosoft.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties()
public class RegisterUserDto {
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String role;
}