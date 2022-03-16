package com.example.rentacarapp.repository;

import com.example.rentacarapp.model.DataHolder;
import com.example.rentacarapp.model.ShoppingCart;
import com.example.rentacarapp.model.ShoppingCartStatus;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ShoppingCartRepository {

    public Optional<ShoppingCart> findById(Long id) {
        return DataHolder.shoppingCarts.stream().filter(i -> i.getId().equals(id)).findFirst();
    }

    public Optional<ShoppingCart> findByUsernameAndStatus(String username, ShoppingCartStatus status) {
        return DataHolder.shoppingCarts.stream()
            .filter(i -> i.getUser().getUsername().equals(username) && i.getStatus().equals(status))
            .findFirst();
    }


    public ShoppingCart save(ShoppingCart shoppingCart) {
        DataHolder.shoppingCarts
            .removeIf(i -> i.getUser().getUsername().equals(shoppingCart.getUser().getUsername()));

        DataHolder.shoppingCarts.add(shoppingCart);
        return shoppingCart;
    }
}
