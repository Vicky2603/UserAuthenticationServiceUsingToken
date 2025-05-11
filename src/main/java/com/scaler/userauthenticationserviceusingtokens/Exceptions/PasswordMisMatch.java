package com.scaler.userauthenticationserviceusingtokens.Exceptions;

public class PasswordMisMatch extends RuntimeException {
    public PasswordMisMatch(String message) {
        super(message);
    }
}