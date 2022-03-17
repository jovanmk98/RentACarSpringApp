package com.example.rentacarapp.controller;

import com.example.rentacarapp.model.Car;
import com.example.rentacarapp.model.Reservation;
import com.example.rentacarapp.service.ReservationService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public String addReservation(HttpServletRequest req
    ) {
        String username = req.getRemoteUser();
        this.reservationService.save(username);
        return "redirect:/cars/list";
    }
    @GetMapping
    public String getCarsPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Reservation> reservations = reservationService.listAll();
        model.addAttribute("reservations", reservations);
        return "reservations";
    }
}
