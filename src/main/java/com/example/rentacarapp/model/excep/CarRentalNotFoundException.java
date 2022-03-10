package com.example.rentacarapp.model.excep;

public class CarRentalNotFoundException extends RuntimeException {

    public CarRentalNotFoundException(Long id) {
        super(String.format("Car rental: %d not found", id));
    }
}
