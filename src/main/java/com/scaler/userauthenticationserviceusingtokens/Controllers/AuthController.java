package com.scaler.userauthenticationserviceusingtokens.Controllers;

import com.scaler.userauthenticationserviceusingtokens.Dto.LoginRequestDto;
import com.scaler.userauthenticationserviceusingtokens.Dto.SignUpRequestDto;
import com.scaler.userauthenticationserviceusingtokens.Dto.UserDto;
import com.scaler.userauthenticationserviceusingtokens.Exceptions.PasswordMisMatch;
import com.scaler.userauthenticationserviceusingtokens.Exceptions.UserNotFoundException;
import com.scaler.userauthenticationserviceusingtokens.Models.User;
import com.scaler.userauthenticationserviceusingtokens.Service.AuthService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDto signUpRequestDto) {

        String email = signUpRequestDto.getEmail();
        String password = signUpRequestDto.getPassword();

        try {
            User user = authService.signup(email, password);
            UserDto userDto = from(user);
            return userDto;
        }
        catch (Exception e) {
            throw e;
        }


    }


    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDto loginRequestDto) {

        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        try {
            User user = authService.login(email, password);
            UserDto userDto = from(user);
            return ResponseEntity.ok(userDto);
        }
        catch (Exception e) {
            throw e;
        }

    }


    public UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        return userDto;
    }


}
