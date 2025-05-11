package com.scaler.userauthenticationserviceusingtokens.Controllers;

import com.scaler.userauthenticationserviceusingtokens.Dto.ErrorMessageDto;
import com.scaler.userauthenticationserviceusingtokens.Dto.LoginRequestDto;
import com.scaler.userauthenticationserviceusingtokens.Dto.SignUpRequestDto;
import com.scaler.userauthenticationserviceusingtokens.Dto.UserDto;
import com.scaler.userauthenticationserviceusingtokens.Exceptions.PasswordMisMatch;
import com.scaler.userauthenticationserviceusingtokens.Exceptions.UserAlreadyExistsException;
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
import org.springframework.web.servlet.View;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private View error;

    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {

        String email = signUpRequestDto.getEmail();
        String password = signUpRequestDto.getPassword();

        try {
            User user = authService.signup(email, password);
            UserDto userDto = from(user);
            return ResponseEntity.ok(userDto);
        }
        catch (UserAlreadyExistsException u) {
            ErrorMessageDto errorMessageDto = new ErrorMessageDto(HttpStatus.UNAUTHORIZED.value(), "USER ALREADY REGISTERED, PLEASE TRY TO LOGIN...");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessageDto);
        }

        catch (Exception e) {
            ErrorMessageDto errorMessageDto = new ErrorMessageDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessageDto);
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
        catch (PasswordMisMatch p) {
            ErrorMessageDto errorMessageDto = new ErrorMessageDto(HttpStatus.UNAUTHORIZED.value(), "ENETERED PASSWORD DOES NOT MATCH WITH THE GIVEN USER...");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessageDto);
        }

        catch (UserNotFoundException p) {
            ErrorMessageDto errorMessageDto = new ErrorMessageDto(HttpStatus.NOT_FOUND.value(), "USER NOT FOUND, PLEASE SIGNUP BEFORE LOGGING IN...");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessageDto);
        }

        catch (Exception e) {
            ErrorMessageDto errorMessageDto = new ErrorMessageDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessageDto);
        }

    }


    public UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        return userDto;
    }


}
