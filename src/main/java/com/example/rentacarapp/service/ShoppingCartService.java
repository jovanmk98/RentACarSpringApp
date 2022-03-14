package com.example.rentacarapp.service;

import com.example.rentacarapp.model.Car;
import com.example.rentacarapp.model.ShoppingCart;
import java.util.List;
import java.util.Optional;

public interface ShoppingCartService {
    List<Car> listAllProducts(Long cartId);

    ShoppingCart getActiveShoppingCart(String username);

    ShoppingCart addProductToShoppingCart(String username, Long productId);

    Optional<Car> findById(Long id, String username);


}
