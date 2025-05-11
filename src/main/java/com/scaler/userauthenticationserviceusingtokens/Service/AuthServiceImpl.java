package com.scaler.userauthenticationserviceusingtokens.Service;

import com.scaler.userauthenticationserviceusingtokens.Exceptions.PasswordMisMatch;
import com.scaler.userauthenticationserviceusingtokens.Exceptions.UserAlreadyExistsException;
import com.scaler.userauthenticationserviceusingtokens.Exceptions.UserNotFoundException;
import com.scaler.userauthenticationserviceusingtokens.Models.User;
import com.scaler.userauthenticationserviceusingtokens.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User signup(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmailEquals(email);
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException("USER ALREADY EXISTS, PLEASE LOGIN...");
        }

        User user = new User();
        user.setEmail(email);
//        user.setPassword(password);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);

        return user;
    }

    @Override
    public User login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmailEquals(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("USER NOT FOUND");
        }

        if(! bCryptPasswordEncoder.matches(password, userOptional.get().getPassword())) {
            throw new PasswordMisMatch("WRONG PASSWORD, PLEASE TRY AGAIN...");
        }

        return userOptional.get();
    }

}
