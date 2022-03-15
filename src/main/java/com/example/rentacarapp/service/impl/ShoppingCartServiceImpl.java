package com.example.rentacarapp.service.impl;

import com.example.rentacarapp.model.Car;
import com.example.rentacarapp.model.ShoppingCart;
import com.example.rentacarapp.model.ShoppingCartStatus;
import com.example.rentacarapp.model.User;
import com.example.rentacarapp.repository.ShoppingCartRepository;
import com.example.rentacarapp.repository.UserRepository;
import com.example.rentacarapp.service.CarService;
import com.example.rentacarapp.service.ShoppingCartService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CarService carService;
    private final UserRepository userRepository;


    @Override
    public List<Car> listAllProducts(Long cartId) {
        return this.shoppingCartRepository.findById(cartId).get().getProducts();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        User active = this.userRepository.findByEmail(username).orElse(null);
        return this.shoppingCartRepository
                .findByUserAndStatus(active, ShoppingCartStatus.CREATED)
                .orElseGet(() -> {
                    User user = this.userRepository.findByEmail(username)
                            .orElseThrow();
                    ShoppingCart shoppingCart = new ShoppingCart(user);
                    return this.shoppingCartRepository.save(shoppingCart);
                });

    }

    @Override
    public ShoppingCart addProductToShoppingCart(String username, Long carId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Car car = this.carService.findById(carId)
                .orElseThrow();
        List<Car> products=shoppingCart.getProducts();
        if(!products.contains(car))
            products.add(car);
        return this.shoppingCartRepository.save(shoppingCart);

    }

    @Override
    public Optional<Car> findById(Long id, String username) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        return shoppingCart.getProducts().stream().filter(i->i.getId().equals(id)).findFirst();
    }
}
