package com.example.rentacarapp.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.rentacarapp.model.Car;
import com.example.rentacarapp.model.CarRentalShop;
import com.example.rentacarapp.model.excep.CarNotFoundException;
import com.example.rentacarapp.model.excep.CarRentalNotFoundException;
import com.example.rentacarapp.repository.CarRentalShopRepository;
import com.example.rentacarapp.repository.CarRepository;
import com.example.rentacarapp.service.CarRentalShopService;
import com.example.rentacarapp.service.CarService;
import com.example.rentacarapp.utils.BaseTestData;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CarServiceImplTest extends BaseTestData {

    @Mock
    CarRepository carRepository;

    @Mock
    CarService carService;

    @Mock
    CarRentalShopService carRentalShopService;

    @Mock
    CarRentalShopRepository carRentalShopRepository;

    @BeforeEach
    void setup() {
        carService = new CarServiceImpl(carRepository, carRentalShopService);
    }

    @Test
    void listAllWithSearch() {
        Car car = getCar();

        when(carRepository.findAllByNameContaining(car.getName())).thenReturn(Stream.of(car).collect(Collectors.toList()));
        List<Car> cars = carService.listAll(car.getName());

        assertThat(cars.size()).isEqualTo(1);
    }

    @Test
    void listWithoutSearch() {
        Car car = getCar();

        when(carRepository.findAll()).thenReturn(Stream.of(car).collect(Collectors.toList()));
        List<Car> cars = carService.listAll(null);

        assertThat(cars.size()).isEqualTo(1);
    }

    @Test
    void findById() {
        Car car = getCar();

        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        Car actual = carService.findById(car.getId()).get();

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(car.getId());
        assertThat(actual.getName()).isEqualTo(car.getName());
        assertThat(actual.getHorsePower()).isEqualTo(car.getHorsePower());
        assertThat(actual.getPrice()).isEqualTo(car.getPrice());
        assertThat(actual.getYear()).isEqualTo(car.getYear());
        assertThat(actual.getImage()).isEqualTo(car.getImage());
        assertThat(actual.getCarRentalShops()).isEqualTo(car.getCarRentalShops());
    }

    @Test
    void addCar() {
        Car car = getCar();

        CarRentalShop carRentalShop = getCarRentalShop();

        when(carRentalShopService.findById(carRentalShop.getId())).thenReturn(Optional.of(carRentalShop));
        when(carRepository.save(any(Car.class))).thenReturn(car);

        Car actual = carService.addCar(car.getName(), car.getPrice(), car.getYear(), car.getHorsePower(),
            car.getImage(), carRentalShop.getId());

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(car.getId());
        assertThat(actual.getName()).isEqualTo(car.getName());
        assertThat(actual.getHorsePower()).isEqualTo(car.getHorsePower());
        assertThat(actual.getPrice()).isEqualTo(car.getPrice());
        assertThat(actual.getYear()).isEqualTo(car.getYear());
        assertThat(actual.getImage()).isEqualTo(car.getImage());
        assertThat(actual.getCarRentalShops()).isEqualTo(car.getCarRentalShops());
    }

    @Test
    void deleteById() {
        Car car = getCar();

        doNothing().when(carRepository).deleteById(car.getId());

        carService.deleteById(car.getId());

        verify(carRepository, times(1)).deleteById(car.getId());
    }

    @Test
    void update() {
        Car car = getCar();

        Car updatedCar = getUpdateCar();

        CarRentalShop carRentalShop = getUpdateCarRentalShop();
        List<CarRentalShop> carRentalShops = new ArrayList<>();
        carRentalShops.add(carRentalShop);
        car.setCarRentalShops(carRentalShops);

        when(carRentalShopService.findById(carRentalShop.getId())).thenReturn(Optional.of(carRentalShop));
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        when(carRepository.save(any(Car.class))).thenReturn(car);

        carService.update(car.getId(), updatedCar.getName(), updatedCar.getPrice(), updatedCar.getYear(),
            updatedCar.getHorsePower(), updatedCar.getImage(), carRentalShop.getId());

        assertThat(car.getName()).isEqualTo(updatedCar.getName());
        assertThat(car.getYear()).isEqualTo(updatedCar.getYear());
        assertThat(car.getPrice()).isEqualTo(updatedCar.getPrice());
        assertThat(car.getHorsePower()).isEqualTo(updatedCar.getHorsePower());
        assertThat(car.getImage()).isEqualTo(updatedCar.getImage());
        assertThat(car.getCarRentalShops()).isEqualTo(updatedCar.getCarRentalShops());

    }

    @Test
    void listCarsFromCarRentalShop() {
        Car car = getCar();
        List<Car> cars = new ArrayList<>();
        cars.add(car);

        CarRentalShop carRentalShop = getCarRentalShop();

        when(carRentalShopService.findById(carRentalShop.getId())).thenReturn(Optional.of(carRentalShop));
        when(carRepository.findByCarRentalShopsContaining(carRentalShop)).thenReturn(cars);
        List<Car> actual = carService.listCarsFromCarRentalShop(carRentalShop.getId());

        assertThat(actual.size()).isEqualTo(cars.size());
    }

    @Test
    void shouldThrowCarRentalNotFoundExceptionWhenAddingCar() {
        Car car = getCar();
        CarRentalShop carRentalShop = getCarRentalShop();

        when(carRentalShopRepository.findById(carRentalShop.getId())).thenReturn(Optional.empty());

        assertThrows(
            CarRentalNotFoundException.class,
            () -> carService.addCar(car.getName(), car.getPrice(), car.getYear(), car.getHorsePower(), car.getImage(),
                carRentalShop.getId()));
    }

    @Test
    void shouldThrowCarNotFoundException() {
        Car car = getCar();
        CarRentalShop carRentalShop = getCarRentalShop();

        when(carRepository.findById(car.getId())).thenReturn(Optional.empty());

        assertThrows(
            CarNotFoundException.class,
            () -> carService.update(car.getId(), car.getName(), car.getPrice(), car.getYear(), car.getHorsePower(),
                car.getImage(), carRentalShop.getId()));
    }
}