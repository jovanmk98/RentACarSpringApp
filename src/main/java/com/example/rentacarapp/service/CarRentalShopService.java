package com.example.rentacarapp.service;

import com.example.rentacarapp.model.CarRentalShop;
import java.util.List;
import java.util.Optional;

public interface CarRentalShopService {

    List<CarRentalShop> listAll();

    Optional<CarRentalShop> findById(Long id);

    CarRentalShop addCarRentalShop(String name, String city, String address);

    void deleteById(Long id);

    void update(Long id, String name, String city, String address);

}
