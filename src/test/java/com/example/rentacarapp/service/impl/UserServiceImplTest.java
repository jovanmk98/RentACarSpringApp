package com.example.rentacarapp.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.rentacarapp.model.User;
import com.example.rentacarapp.model.excep.EmailNotFoundException;
import com.example.rentacarapp.model.excep.InvalidInputException;
import com.example.rentacarapp.model.excep.PasswordsDoNotMatchException;
import com.example.rentacarapp.model.excep.UserNotFoundException;
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

    private static final String REPEAT_PASSWORD = "password";

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
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

        when(userRepository.save(any(User.class))).thenReturn(user);

        User actual = userService.register(user.getEmail(), user.getPassword(), REPEAT_PASSWORD, user.getName(),
            user.getLastName());

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(user.getId());
        assertThat(actual.getEmail()).isEqualTo(user.getEmail());
        assertThat(actual.getName()).isEqualTo(user.getName());
        assertThat(actual.getLastName()).isEqualTo(user.getLastName());
        assertThat(actual.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    void loadUserByUsername() {
        User user = getUser();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        UserDetails actual = userService.loadUserByUsername(user.getEmail());

        assertThat(actual).isNotNull();
        assertThat(actual.getUsername()).isEqualTo(user.getEmail());
        assertThat(actual.getPassword()).isEqualTo(user.getPassword());
        assertThat(actual.getAuthorities()).isEqualTo(user.getAuthorities());
    }

    @Test
    void shouldThrowUserNotFoundException() {
        User user = getUser();

        when(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.login(user.getEmail(), user.getPassword()));
    }

    @Test
    void shouldThrowInvalidInputExceptionWhenEmailIsEmpty() {
        User user = getUser();
        user.setEmail("");

        assertThrows(InvalidInputException.class,
            () -> userService.register(user.getEmail(), user.getPassword(), REPEAT_PASSWORD, user.getName(),
                user.getLastName()));
    }

    @Test
    void shouldThrowPasswordsDoNotMatchExceptionWhenPasswordsDoNotMatch() {
        User user = getUser();

        assertThrows(PasswordsDoNotMatchException.class,
            () -> userService.register(user.getEmail(), user.getPassword(), REPEAT_PASSWORD + ".", user.getName(),
                user.getLastName()));
    }

    @Test
    void shouldThrowEmailNotFoundException() {
        User user = getUser();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        assertThrows(EmailNotFoundException.class, () -> userService.loadUserByUsername(user.getEmail()));
    }

}