package com.gosoft.ecommerce.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gosoft.ecommerce.enums.IType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalResponse<T> {
    private String message;
    private String code;
    private T data;

    public GlobalResponse(IType type) {
        this.message = type.getMessage();
        this.code = type.getCode();
    }

    public GlobalResponse(IType type, Object... str) {
        this.code = type.getCode();
        this.message = String.format(type.getMessage(), str);
    }

    public GlobalResponse(T data) {
        this.data = data;
    }
}
