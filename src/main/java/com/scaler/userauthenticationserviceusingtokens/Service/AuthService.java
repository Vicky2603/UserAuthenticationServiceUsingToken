package com.scaler.userauthenticationserviceusingtokens.Service;

import com.scaler.userauthenticationserviceusingtokens.Models.User;

public interface AuthService {
    public User signup(String email, String password);
    public User login(String email, String password);
}
