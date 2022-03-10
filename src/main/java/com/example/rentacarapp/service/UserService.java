package com.example.rentacarapp.service;

import com.example.rentacarapp.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User login(String email, String password);

    User register(String email, String password, String repeatPassword, String name, String lastName);
}
