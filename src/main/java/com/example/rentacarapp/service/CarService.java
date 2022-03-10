package com.example.rentacarapp.service;

import com.example.rentacarapp.model.Car;
import java.util.List;
import java.util.Optional;

public interface CarService {

    List<Car> listAll();

    Optional<Car> findById(Long id);

    Car addCar(String name, Integer price, Integer year, Integer horsepower, String image, Long carRental);

    void deleteById(Long id);

    void update(Long id, String name, Integer price, Integer year, Integer horsepower, String image, Long carRental);

    List<Car> listCarsFromCarRentalShop(Long id);
}
