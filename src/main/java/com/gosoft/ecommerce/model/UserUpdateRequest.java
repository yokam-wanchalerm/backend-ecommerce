package com.gosoft.ecommerce.model;

import lombok.Getter;

@Getter
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
