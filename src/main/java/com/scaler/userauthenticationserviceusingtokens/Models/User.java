package com.scaler.userauthenticationserviceusingtokens.Models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class User extends baseModel {
    private String email;
    private String password;
}
