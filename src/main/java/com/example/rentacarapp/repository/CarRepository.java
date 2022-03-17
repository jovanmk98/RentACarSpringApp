package com.example.rentacarapp.repository;

import com.example.rentacarapp.model.Car;
import com.example.rentacarapp.model.CarRentalShop;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByCarRentalShopsContaining(CarRentalShop carRentalShop);

    List<Car> findAllByNameContaining(String name);
}
