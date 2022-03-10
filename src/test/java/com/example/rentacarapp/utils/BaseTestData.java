package com.example.rentacarapp.utils;

import com.example.rentacarapp.model.Car;
import com.example.rentacarapp.model.CarRentalShop;
import com.example.rentacarapp.model.User;
import com.example.rentacarapp.model.enumerations.Role;
import java.util.ArrayList;
import java.util.List;

public class BaseTestData {

    protected Car getCar() {
        List<CarRentalShop> carRentalShops = new ArrayList<>();
        CarRentalShop carRentalShop = getCarRentalShop();
        carRentalShops.add(carRentalShop);
        return Car.builder()
            .id(1L)
            .name("name")
            .price(123)
            .year(2000)
            .horsePower(145)
            .image("image")
            .carRentalShops(carRentalShops)
            .build();
    }

    protected Car getUpdateCar() {
        List<CarRentalShop> carRentalShops = new ArrayList<>();
        CarRentalShop carRentalShop = getUpdateCarRentalShop();
        carRentalShops.add(carRentalShop);
        return Car.builder()
            .id(1L)
            .name("update-name")
            .price(1234)
            .year(2001)
            .horsePower(146)
            .image("update-image")
            .carRentalShops(carRentalShops)
            .build();
    }

    protected CarRentalShop getCarRentalShop() {
        return CarRentalShop.builder()
            .id(1L)
            .name("name")
            .city("city")
            .address("address")
            .build();
    }

    protected CarRentalShop getUpdateCarRentalShop() {
        return CarRentalShop.builder()
            .name("update-name")
            .city("update-city")
            .address("update-address")
            .build();
    }

    protected User getUser() {
        return User.builder()
            .name("name")
            .lastName("lastName")
            .password("password")
            .email("email@outlook.com")
            .id(1L)
            .role(Role.ROLE_ADMIN)
            .build();
    }
}
