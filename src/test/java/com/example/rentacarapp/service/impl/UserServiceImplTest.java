package com.example.rentacarapp.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.example.rentacarapp.model.User;
import com.example.rentacarapp.repository.UserRepository;
import com.example.rentacarapp.service.UserService;
import com.example.rentacarapp.utils.BaseTestData;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest extends BaseTestData {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup(){
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    void login() {
        User user = getUser();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserDetails actual = userService.loadUserByUsername(user.getEmail());

        assertThat(actual).isNotNull();
        assertThat(actual.getUsername()).isEqualTo(user.getUsername());
        assertThat(actual.getPassword()).isEqualTo(user.getPassword());
        assertThat(actual.getAuthorities()).isEqualTo(user.getAuthorities());
    }

    @Test
    void register() {
        User user = getUser();

        String repeatPassword = user.getPassword();

        when(userRepository.save(user)).thenReturn(user);

        User actual = userService.register(user.getEmail(), user.getPassword(), repeatPassword, user.getName(), user.getLastName());

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(user.getId());
        assertThat(actual.getEmail()).isEqualTo(user.getEmail());
        assertThat(actual.getName()).isEqualTo(user.getName());
        assertThat(actual.getLastName()).isEqualTo(user.getLastName());
        assertThat(actual.getPassword()).isEqualTo(user.getPassword());

    }

    @Test
    void loadUserByUsername() {
    }
}