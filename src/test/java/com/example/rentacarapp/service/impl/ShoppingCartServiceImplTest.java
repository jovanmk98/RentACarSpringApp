package com.example.rentacarapp.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.rentacarapp.model.Car;
import com.example.rentacarapp.model.ShoppingCart;
import com.example.rentacarapp.model.enumerations.ShoppingCartStatus;
import com.example.rentacarapp.repository.ShoppingCartRepository;
import com.example.rentacarapp.repository.UserRepository;
import com.example.rentacarapp.service.CarService;
import com.example.rentacarapp.service.ShoppingCartService;
import com.example.rentacarapp.utils.BaseTestData;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ShoppingCartServiceImplTest extends BaseTestData {

    @Mock
    CarService carService;

    @Mock
    UserRepository userRepository;

    @Mock
    ShoppingCartRepository shoppingCartRepository;

    @Mock
    ShoppingCartService shoppingCartService;


    @BeforeEach
    void setup() {
        shoppingCartService = new ShoppingCartServiceImpl(shoppingCartRepository, carService, userRepository);
    }

    @Test
    void listAllProducts() {
        ShoppingCart shoppingCart = getShoppingCart();
        Car car = shoppingCart.getProduct();

        when(shoppingCartRepository.findById(shoppingCart.getId())).thenReturn(Optional.of(shoppingCart));

        Car actual = shoppingCartService.listAllProducts(shoppingCart.getId());

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
    void getActiveShoppingCart() {
        ShoppingCart shoppingCart = getShoppingCart();
        String username = shoppingCart.getUser().getEmail();

        when(shoppingCartRepository.findByUsernameAndStatus(username, ShoppingCartStatus.CREATED)).thenReturn(
            Optional.of(shoppingCart));

        ShoppingCart actual = shoppingCartService.getActiveShoppingCart(username);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(shoppingCart.getId());
        assertThat(actual.getDateFrom()).isEqualTo(shoppingCart.getDateFrom());
        assertThat(actual.getDateTo()).isEqualTo(shoppingCart.getDateTo());
        assertThat(actual.getDateCreated()).isEqualTo(shoppingCart.getDateCreated());
        assertThat(actual.getProduct()).isEqualTo(shoppingCart.getProduct());
        assertThat(actual.getUser()).isEqualTo(shoppingCart.getUser());

    }

    @Test
    void addProductToShoppingCart() {
        ShoppingCart shoppingCart = getShoppingCart();
        Car car = shoppingCart.getProduct();
        String username = shoppingCart.getUser().getEmail();

        when(shoppingCartRepository.findByUsernameAndStatus(username, ShoppingCartStatus.CREATED)).thenReturn(
            Optional.of(shoppingCart));
        when(carService.findById(car.getId())).thenReturn(Optional.of(car));
        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenReturn(shoppingCart);

        ShoppingCart actual = shoppingCartService.addProductToShoppingCart(username, car.getId());

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(shoppingCart.getId());
        assertThat(actual.getDateFrom()).isEqualTo(shoppingCart.getDateFrom());
        assertThat(actual.getDateTo()).isEqualTo(shoppingCart.getDateTo());
        assertThat(actual.getDateCreated()).isEqualTo(shoppingCart.getDateCreated());
        assertThat(actual.getProduct()).isEqualTo(shoppingCart.getProduct());
        assertThat(actual.getUser()).isEqualTo(shoppingCart.getUser());
    }

    @Test
    void findById() {
        ShoppingCart shoppingCart = getShoppingCart();
        String username = shoppingCart.getUser().getEmail();
        Car car = shoppingCart.getProduct();

        when(shoppingCartRepository.findByUsernameAndStatus(username, ShoppingCartStatus.CREATED)).thenReturn(
            Optional.of(shoppingCart));

        Optional<Car> actual = shoppingCartService.findById(car.getId(), username);

        assertThat(actual).isNotNull();
    }
}