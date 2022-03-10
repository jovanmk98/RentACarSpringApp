package com.example.rentacarapp.service.impl;

import com.example.rentacarapp.model.CarRentalShop;
import com.example.rentacarapp.model.excep.CarRentalNotFoundException;
import com.example.rentacarapp.model.excep.InvalidInputException;
import com.example.rentacarapp.repository.CarRentalShopRepository;
import com.example.rentacarapp.service.CarRentalShopService;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CarRentalShopServiceImpl implements CarRentalShopService {

    private final CarRentalShopRepository carRentalShopRepository;

    @Override
    public List<CarRentalShop> listAll() {
        return carRentalShopRepository.findAll().stream().sorted(Comparator.comparing(CarRentalShop::getName)).collect(
            Collectors.toList());
    }

    @Override
    public Optional<CarRentalShop> findById(Long id) {
        return carRentalShopRepository.findById(id);
    }

    @Override
    public CarRentalShop addCarRentalShop(String name, String city, String address) {
        CarRentalShop carRentalShop = CarRentalShop.builder().name(name)
            .address(address)
            .city(city).build();
        return carRentalShopRepository.save(carRentalShop);
    }


    @Override
    public void deleteById(Long id) {
        this.carRentalShopRepository.deleteById(id);
    }

    @Override
    public void update(Long id, String name, String city, String address) {
        CarRentalShop carRentalShop= this.carRentalShopRepository.findById(id).orElseThrow(()->new CarRentalNotFoundException(id));

        carRentalShop.setName(name);
        carRentalShop.setAddress(address);
        carRentalShop.setCity(city);

        this.carRentalShopRepository.save(carRentalShop);
    }
}
