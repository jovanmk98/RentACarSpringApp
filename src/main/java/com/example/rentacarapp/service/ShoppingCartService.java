package com.example.rentacarapp.service;

import com.example.rentacarapp.model.Car;
import com.example.rentacarapp.model.ShoppingCart;
import java.util.Optional;

public interface ShoppingCartService {

    Car listAllProducts(Long cartId);

    ShoppingCart getActiveShoppingCart(String username);

    ShoppingCart addProductToShoppingCart(String username, Long productId);

    Optional<Car> findById(Long id, String username);

    Integer calculatePrice(Long id, String dateFrom, String dateTo);
}
