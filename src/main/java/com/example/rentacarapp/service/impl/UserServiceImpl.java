package com.example.rentacarapp.service.impl;

import com.example.rentacarapp.model.User;
import com.example.rentacarapp.model.enumerations.Role;
import com.example.rentacarapp.model.excep.EmailNotFoundException;
import com.example.rentacarapp.model.excep.InvalidInputException;
import com.example.rentacarapp.model.excep.PasswordsDoNotMatchException;
import com.example.rentacarapp.model.excep.UserNotFoundException;
import com.example.rentacarapp.repository.UserRepository;
import com.example.rentacarapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
            .orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public User register(String email, String password, String repeatPassword, String name, String lastName) {

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidInputException();
        }
        if (!password.equals(repeatPassword)) {
            throw new PasswordsDoNotMatchException(password, repeatPassword);
        }
        User user = User.builder().email(email)
            .password(passwordEncoder.encode(password))
            .name(name)
            .role(Role.ROLE_USER)
            .lastName(lastName).build();
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new EmailNotFoundException(username));
    }
}
