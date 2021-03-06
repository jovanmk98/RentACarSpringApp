package com.example.rentacarapp.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.rentacarapp.model.Car;
import com.example.rentacarapp.model.Reservation;
import com.example.rentacarapp.model.User;
import com.example.rentacarapp.model.enumerations.ReservationStatus;
import com.example.rentacarapp.repository.CarRepository;
import com.example.rentacarapp.repository.ReservationRepository;
import com.example.rentacarapp.repository.UserRepository;
import com.example.rentacarapp.service.CarService;
import com.example.rentacarapp.service.ReservationService;
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
class ReservationServiceImplTest extends BaseTestData {

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    ReservationService reservationService;

    @Mock
    UserRepository userRepository;

    @Mock
    CarRepository carRepository;

    @Mock
    CarService carService;

    @BeforeEach
    void setup() {
        reservationService = new ReservationServiceImpl(reservationRepository, userRepository, carRepository,
            carService);
    }

    @Test
    void listAll() {
        Car car = getCar();
        User user = getUser();
        Reservation reservation = getReservation(user, car);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(reservationRepository.findAllByUserAndReservationStatus(user, ReservationStatus.ACTIVE)).thenReturn(
            Stream.of(reservation).collect(Collectors.toList()));
        List<Reservation> reservations = reservationService.listAll(user.getEmail());

        assertThat(reservations.size()).isEqualTo(1);
    }
}