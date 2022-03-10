package com.example.rentacarapp.controller;

import com.example.rentacarapp.model.CarRentalShop;
import com.example.rentacarapp.service.CarRentalShopService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/car-rental")
@RequiredArgsConstructor
public class CarRentalShopController {

    private final CarRentalShopService carRentalShopService;

    @GetMapping("/list")
    public String getCarRentalPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<CarRentalShop> carRentalShops = this.carRentalShopService.listAll();
        model.addAttribute("carRentalShops", carRentalShops);
        return "carRental/car-rental-shops";
    }

    @PostMapping("/add")
    public String addCarRental(
        @RequestParam String name,
        @RequestParam String city,
        @RequestParam String address
    ) {
        this.carRentalShopService.addCarRentalShop(name, city, address);
        return "redirect:/car-rental/list";
    }

    @GetMapping("/addCarRental")
    public String showAddCarRentalForm(Model theModel) {
        CarRentalShop carRentalShop = new CarRentalShop();

        theModel.addAttribute("carRentalShop", carRentalShop);
        return "carRental/add-car-rental";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCarRentalShop(@PathVariable Long id) {
        this.carRentalShopService.deleteById(id);
        return "redirect:/car-rental/list";
    }

    @PostMapping("/update/{id}")
    public String updateCarRental(
        @PathVariable Long id,
        @RequestParam String name,
        @RequestParam String city,
        @RequestParam String address
    ) {
        this.carRentalShopService.update(id, name, city, address);
        return "redirect:/car-rental/list";
    }

    @GetMapping("/edit-form/{id}")
    public String editCarRental(@PathVariable Long id, Model model) {
        CarRentalShop carRental = this.carRentalShopService.findById(id).get();
        model.addAttribute("carRental", carRental);

        return "carRental/edit-car-rental";
    }

}
