package com.example.rentacarapp.model.excep;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String text) {
        super("%s cannot be empty");
    }
}
