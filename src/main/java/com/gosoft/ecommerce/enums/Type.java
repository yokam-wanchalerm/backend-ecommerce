package com.gosoft.ecommerce.enums;

import lombok.Getter;

@Getter
public enum Type implements IType{
    EMAIL_EXIST                     ("4001", "Email %s is already exist"),
    USER_NOT_FOUND                  ("4002", "UserID %s is not found.");

    private final String code;
    private final String message;

    Type(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
