package com.example.rentacarapp.service.impl;

import com.example.rentacarapp.model.Car;
import com.example.rentacarapp.model.DataHolder;
import com.example.rentacarapp.model.ShoppingCart;
import com.example.rentacarapp.model.enumerations.ShoppingCartStatus;
import com.example.rentacarapp.model.User;
import com.example.rentacarapp.model.excep.CarNotFoundException;
import com.example.rentacarapp.repository.ShoppingCartRepository;
import com.example.rentacarapp.repository.UserRepository;
import com.example.rentacarapp.service.CarService;
import com.example.rentacarapp.service.ShoppingCartService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CarService carService;
    private final UserRepository userRepository;

    @Override
    public Car listAllProducts(Long cartId) {
        return this.shoppingCartRepository.findById(cartId).get().getProduct();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        return this.shoppingCartRepository
            .findByUsernameAndStatus(username, ShoppingCartStatus.CREATED)
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
        shoppingCart.setProduct(car);
        return this.shoppingCartRepository.save(shoppingCart);

    }

    @Override
    public Optional<Car> findById(Long id, String username) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        return Optional.of(shoppingCart.getProduct());
    }

    @Override
    public Integer calculatePrice(Long id, String dateFrom, String dateTo) {
        Car car = carService.findById(id).orElseThrow(() -> new CarNotFoundException(id));
        ShoppingCart shoppingCart = DataHolder.shoppingCarts.get(DataHolder.shoppingCarts.size()-1);
        shoppingCart.setDateFrom(dateFrom);
        shoppingCart.setDateTo(dateTo);
        try {
            long days = getDaysForReservation(dateFrom, dateTo);
            return (int) (days * car.getPrice());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return car.getPrice();
    }

    private static long getDaysForReservation(String dateFrom, String dateTo) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date firstDate = sdf.parse(dateFrom);
        Date secondDate = sdf.parse(dateTo);

        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
