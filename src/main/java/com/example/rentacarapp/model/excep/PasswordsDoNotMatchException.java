package com.example.rentacarapp.model.excep;

public class PasswordsDoNotMatchException extends RuntimeException {
    public PasswordsDoNotMatchException() {
        super(String.format("Passwords do not match"));
    }
}