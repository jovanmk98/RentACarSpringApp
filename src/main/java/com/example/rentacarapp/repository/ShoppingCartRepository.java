package com.example.rentacarapp.repository;

import com.example.rentacarapp.model.DataHolder;
import com.example.rentacarapp.model.ShoppingCart;
import com.example.rentacarapp.model.ShoppingCartStatus;
import com.example.rentacarapp.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserAndStatus(User user, ShoppingCartStatus status);
}
