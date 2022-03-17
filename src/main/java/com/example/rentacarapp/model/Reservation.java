package com.example.rentacarapp.model;

import com.example.rentacarapp.model.enumerations.ReservationStatus;
import com.example.rentacarapp.model.enumerations.Role;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastname;

    private Integer totalPrice;

    @Enumerated(value = EnumType.STRING)
    private ReservationStatus reservationStatus;

    @ManyToOne
    private User user;

    @OneToOne
    private Car car;

}
