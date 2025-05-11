package com.scaler.userauthenticationserviceusingtokens.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorMessageDto {
    private int statusCode;
    private String message;

    public ErrorMessageDto(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

}
