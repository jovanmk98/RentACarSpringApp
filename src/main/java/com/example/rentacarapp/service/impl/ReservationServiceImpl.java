package com.example.rentacarapp.service.impl;

import com.example.rentacarapp.model.Car;
import com.example.rentacarapp.model.DataHolder;
import com.example.rentacarapp.model.Reservation;
import com.example.rentacarapp.model.ShoppingCart;
import com.example.rentacarapp.model.User;
import com.example.rentacarapp.model.enumerations.ReservationStatus;
import com.example.rentacarapp.model.excep.CarNotFoundException;
import com.example.rentacarapp.model.excep.UserNotFoundException;
import com.example.rentacarapp.repository.CarRepository;
import com.example.rentacarapp.repository.ReservationRepository;
import com.example.rentacarapp.repository.UserRepository;
import com.example.rentacarapp.service.CarService;
import com.example.rentacarapp.service.ReservationService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final CarService carService;

    @Override
    public Reservation save(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        ShoppingCart shoppingCart = DataHolder.shoppingCarts.get(0);
        Car car = shoppingCart.getProduct();
        Car changeCar = carService.findById(car.getId()).orElseThrow(() -> new CarNotFoundException(car.getId()));
        changeCar.setIsAvailable(false);
        carRepository.save(changeCar);
        Reservation reservation = Reservation.builder()
            .car(shoppingCart.getProduct())
            .name(user.getName())
            .lastname(user.getLastName())
            .totalPrice(car.getPrice())
            .user(user)
            .reservationStatus(ReservationStatus.ACTIVE)
            .build();
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> listAll(String username) {
        if (username.equals("admin@outlook.com")) {
            return reservationRepository.findAll().stream().sorted(Comparator.comparing(Reservation::getReservationStatus)).collect(
                Collectors.toList());
        }
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UserNotFoundException(username));
        return reservationRepository.findAllByUser(user).stream().sorted(Comparator.comparing(Reservation::getReservationStatus)).collect(
            Collectors.toList());
    }

    @Scheduled(cron = "0/15 * * * * *")
    protected void checkForInactiveReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
//        for (Reservation reservation : reservations){
//            if (checkReservationTime(reservation.g))
//        }

    }

    private boolean checkReservationTime(String date)throws ParseException {
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        Date reservationDate = sdf.parse(date);
        return currentTimeMillis>reservationDate.getTime();
    }
}
