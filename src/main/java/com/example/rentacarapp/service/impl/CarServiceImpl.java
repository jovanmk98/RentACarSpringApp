package com.example.rentacarapp.service.impl;

import com.example.rentacarapp.model.Car;
import com.example.rentacarapp.model.CarRentalShop;
import com.example.rentacarapp.model.DataHolder;
import com.example.rentacarapp.model.excep.CarNotFoundException;
import com.example.rentacarapp.model.excep.CarRentalNotFoundException;
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
    public List<Car> listAll(String name) {
        if (DataHolder.shoppingCarts != null) {
            if (!DataHolder.shoppingCarts.isEmpty()) {
                Car shoppingCartCar = DataHolder.shoppingCarts.get(DataHolder.shoppingCarts.size() - 1)
                    .getProduct();
                if (shoppingCartCar != null) {
                    List<Car> cars = carRepository.findAll().stream()
                        .filter(car -> car.getIsAvailable().equals(true))
                        .sorted(Comparator.comparing(Car::getName))
                        .collect(Collectors.toList());
                    if (name != null) {
                        cars = carRepository.findAllByNameContaining(name).stream()
                            .filter(car -> car.getIsAvailable().equals(true))
                            .sorted(Comparator.comparing(Car::getName))
                            .collect(Collectors.toList());
                    }
                    List<Car> availableCars = new ArrayList<>();
                    if (!cars.isEmpty()) {
                        cars.forEach(c -> {

                            boolean flag = !shoppingCartCar.getId().equals(c.getId());
                            if (flag) {
                                availableCars.add(c);
                            }

                        });
                        return availableCars.stream().filter(car -> car.getIsAvailable().equals(true))
                            .sorted(Comparator.comparing(Car::getName))
                            .collect(Collectors.toList());
                    }
                }
            }
        }
        if (name == null) {
            return carRepository.findAll().stream()
                .filter(car -> car.getIsAvailable().equals(true))
                .sorted(Comparator.comparing(Car::getName))
                .collect(Collectors.toList());
        }
        return carRepository.findAllByNameContaining(name).stream().filter(car -> car.getIsAvailable().equals(true))
            .sorted(Comparator.comparing(Car::getName))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);
    }

    @Override
    public Car addCar(String name, Integer price, Integer year, Integer horsepower, String image, Long carRental) {
        CarRentalShop carRentalShop = this.carRentalShopService.findById(carRental)
            .orElseThrow(() -> new CarRentalNotFoundException(carRental));

        List<CarRentalShop> carRentalShops = new ArrayList<>();
        carRentalShops.add(carRentalShop);

        Car car = Car.builder().name(name)
            .price(price)
            .year(year)
            .horsePower(horsepower)
            .carRentalShops(carRentalShops)
            .isAvailable(true)
            .image(image).build();

        return carRepository.save(car);
    }

    @Override
    public void deleteById(Long id) {
        this.carRepository.deleteById(id);
    }

    @Override
    public void update(Long id, String name, Integer price, Integer year, Integer horsepower, String image,
        Long carRental) {
        Car car = this.carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
        car.setName(name);
        car.setPrice(price);
        car.setYear(year);
        car.setHorsePower(horsepower);
        car.setImage(image);

        CarRentalShop carRentalShop = this.carRentalShopService.findById(carRental)
            .orElseThrow(() -> new CarRentalNotFoundException(carRental));
        if (!car.getCarRentalShops().contains(carRentalShop)) {
            car.getCarRentalShops().add(carRentalShop);
        }

        this.carRepository.save(car);
    }

    @Override
    public List<Car> listCarsFromCarRentalShop(Long id) {
        CarRentalShop carRentalShop = this.carRentalShopService.findById(id)
            .orElseThrow(() -> new CarRentalNotFoundException(id));
        return this.carRepository.findByCarRentalShopsContaining(carRentalShop).stream()
            .filter(car -> car.getIsAvailable().equals(true))
            .sorted(Comparator.comparing(Car::getName))
            .collect(Collectors.toList());
    }
}
