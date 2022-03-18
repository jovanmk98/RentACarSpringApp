package com.example.rentacarapp.repository;

import com.example.rentacarapp.model.Reservation;
import com.example.rentacarapp.model.User;
import com.example.rentacarapp.model.enumerations.ReservationStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByUserAndReservationStatus(User user, ReservationStatus reservationStatus);

    List<Reservation> findAllByReservationStatus(ReservationStatus reservationStatus);
}
