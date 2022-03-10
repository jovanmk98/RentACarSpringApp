package com.example.rentacarapp.service.impl;

import com.example.rentacarapp.model.Car;
import com.example.rentacarapp.model.CarRentalShop;
import com.example.rentacarapp.model.excep.InvalidInputException;
import com.example.rentacarapp.repository.CarRepository;
import com.example.rentacarapp.service.CarRentalShopService;
import com.example.rentacarapp.service.CarService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarRentalShopService carRentalShopService;

    @Override
    public List<Car> listAll() {
        return carRepository.findAll().stream().sorted(Comparator.comparing(Car::getName)).collect(Collectors.toList());
    }

    @Override
    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);
    }

    @Override
    public Car addCar(String name, Integer price, Integer year, Integer horsepower, String image, Long carRental) {
        CarRentalShop carRentalShop = this.carRentalShopService.findById(carRental).orElseThrow(InvalidInputException::new);

        List<CarRentalShop> carRentalShops = new ArrayList<>();
        carRentalShops.add(carRentalShop);

        Car car = Car.builder().name(name)
            .price(price)
            .year(year)
            .horsePower(horsepower)
            .carRentalShops(carRentalShops)
            .image(image).build();


        return carRepository.save(car);
    }

    @Override
    public void deleteById(Long id) {
        this.carRepository.deleteById(id);
    }

    @Override
    public void update(Long id, String name, Integer price, Integer year, Integer horsepower, String image, Long carRental) {
        Car car=this.carRepository.findById(id).orElseThrow(InvalidInputException::new);
        car.setName(name);
        car.setPrice(price);
        car.setYear(year);
        car.setHorsePower(horsepower);
        car.setImage(image);

        CarRentalShop carRentalShop = this.carRentalShopService.findById(carRental).orElseThrow(InvalidInputException::new);
        if (!car.getCarRentalShops().contains(carRentalShop)){
            car.getCarRentalShops().add(carRentalShop);
        }

        this.carRepository.save(car);
    }

    @Override
    public List<Car> listCarsFromCarRentalShop(Long id) {
        CarRentalShop carRentalShop = this.carRentalShopService.findById(id).orElseThrow(InvalidInputException::new);
        return this.carRepository.findByCarRentalShopsContaining(carRentalShop);
    }
}
