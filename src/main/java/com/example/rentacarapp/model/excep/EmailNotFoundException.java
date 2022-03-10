package com.example.rentacarapp.model.excep;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException(String email) {
        super(String.format("Email: %s not found", email));
    }
}
