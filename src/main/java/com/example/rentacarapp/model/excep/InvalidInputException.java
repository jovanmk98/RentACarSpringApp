package com.example.rentacarapp.model.excep;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException() {
        super("Invalid input");
    }
}
