package com.example.rentacarapp.repository;

import com.example.rentacarapp.model.CarRentalShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRentalShopRepository extends JpaRepository<CarRentalShop, Long> {

}
