package com.chtran.expensetrackerproject.service;

import com.chtran.expensetrackerproject.entity.Auth;
import com.chtran.expensetrackerproject.entity.User;
import com.chtran.expensetrackerproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository repository;

    public String logIn(Auth user) {
        User existingUser = repository.findByUsername(user.getUsername()).orElse(null);

        if (existingUser == null) {
            return "Cannot find username.";
        }

        if (existingUser.getPassword().equals(user.getPassword()) ) {
            return "Successfully logged in.";
        }

        return "Password is incorrect.";

    }


}
