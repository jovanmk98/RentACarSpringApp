package com.example.rentacarapp.model.excep;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username) {
        super(String.format("User: %s not found", username));
    }

}
