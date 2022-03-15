package com.example.rentacarapp.model.excep;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {
        super(String.format("Email: %s already exists", email));
    }
}
