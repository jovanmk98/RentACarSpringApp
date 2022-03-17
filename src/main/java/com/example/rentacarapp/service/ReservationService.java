package com.example.rentacarapp.service;

import com.example.rentacarapp.model.Reservation;
import java.util.List;

public interface ReservationService {

    Reservation save(String username);

    List<Reservation> listAll(String username);

}
