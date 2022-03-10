package com.example.rentacarapp.model.excep;

public class PasswordsDoNotMatchException extends RuntimeException {
    public PasswordsDoNotMatchException(String password, String repeatPassword) {
        super(String.format("Passwords do not match: %s %s", password, repeatPassword));
    }
}