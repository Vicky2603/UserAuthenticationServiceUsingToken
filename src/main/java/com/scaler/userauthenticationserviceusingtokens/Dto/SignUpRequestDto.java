package com.scaler.userauthenticationserviceusingtokens.Dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpRequestDto {
    private String email;
    private String password;
}
