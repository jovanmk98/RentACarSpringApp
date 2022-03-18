package com.example.rentacarapp.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.rentacarapp.model.CarRentalShop;
import com.example.rentacarapp.model.excep.CarRentalNotFoundException;
import com.example.rentacarapp.repository.CarRentalShopRepository;
import com.example.rentacarapp.service.CarRentalShopService;
import com.example.rentacarapp.utils.BaseTestData;
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
class CarRentalShopServiceImplTest extends BaseTestData {

    @Mock
    CarRentalShopRepository carRentalShopRepository;

    @Mock
    CarRentalShopService carRentalShopService;

    @BeforeEach
    void setup() {
        carRentalShopService = new CarRentalShopServiceImpl(carRentalShopRepository);
    }

    @Test
    void listAll() {
        CarRentalShop carRentalShop = getCarRentalShop();

        when(carRentalShopRepository.findAll()).thenReturn(Stream.of(carRentalShop).collect(Collectors.toList()));
        List<CarRentalShop> carRentalShops = carRentalShopService.listAll();

        assertThat(carRentalShops.size()).isEqualTo(1);
    }

    @Test
    void findById() {
        CarRentalShop carRentalShop = getCarRentalShop();

        when(carRentalShopRepository.findById(carRentalShop.getId())).thenReturn(Optional.of(carRentalShop));
        CarRentalShop actual = carRentalShopService.findById(carRentalShop.getId()).get();

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(carRentalShop.getId());
        assertThat(actual.getName()).isEqualTo(carRentalShop.getName());
        assertThat(actual.getAddress()).isEqualTo(carRentalShop.getAddress());
        assertThat(actual.getCity()).isEqualTo(carRentalShop.getCity());
        assertThat(actual.getAddress()).isEqualTo(carRentalShop.getAddress());
    }

    @Test
    void addCarRentalShop() {
        CarRentalShop carRentalShop = getCarRentalShop();

        when(carRentalShopRepository.save(any(CarRentalShop.class))).thenReturn(carRentalShop);
        CarRentalShop actual = carRentalShopService.addCarRentalShop(carRentalShop.getName(), carRentalShop.getCity(),
            carRentalShop.getAddress());

        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(carRentalShop.getName());
        assertThat(actual.getAddress()).isEqualTo(carRentalShop.getAddress());
        assertThat(actual.getCity()).isEqualTo(carRentalShop.getCity());
        assertThat(actual.getAddress()).isEqualTo(carRentalShop.getAddress());
    }

    @Test
    void deleteById() {
        CarRentalShop carRentalShop = getCarRentalShop();

        doNothing().when(carRentalShopRepository).deleteById(carRentalShop.getId());

        carRentalShopService.deleteById(carRentalShop.getId());

        verify(carRentalShopRepository, times(1)).deleteById(carRentalShop.getId());
    }

    @Test
    void update() {
        CarRentalShop carRentalShop = getCarRentalShop();
        CarRentalShop updateCarRentalShop = getUpdateCarRentalShop();

        when(carRentalShopRepository.findById(carRentalShop.getId())).thenReturn(Optional.of(carRentalShop));
        when(carRentalShopRepository.save(any(CarRentalShop.class))).thenReturn(carRentalShop);

        carRentalShopService.update(carRentalShop.getId(), updateCarRentalShop.getName(), updateCarRentalShop.getCity(),
            updateCarRentalShop.getAddress());

        assertThat(carRentalShop.getName()).isEqualTo(updateCarRentalShop.getName());
        assertThat(carRentalShop.getCity()).isEqualTo(updateCarRentalShop.getCity());
        assertThat(carRentalShop.getAddress()).isEqualTo(updateCarRentalShop.getAddress());
    }

    @Test
    void shouldThrowCarRentalNotFoundException() {
        CarRentalShop carRentalShop = getCarRentalShop();

        when(carRentalShopRepository.findById(carRentalShop.getId())).thenReturn(Optional.empty());

        assertThrows(CarRentalNotFoundException.class,
            () -> carRentalShopService.update(carRentalShop.getId(), carRentalShop.getName(), carRentalShop.getCity(),
                carRentalShop.getAddress()));
    }
}