package com.example.rentacarapp.controller;

import com.example.rentacarapp.model.Car;
import com.example.rentacarapp.model.CarRentalShop;
import com.example.rentacarapp.service.CarRentalShopService;
import com.example.rentacarapp.service.CarService;
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
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final CarRentalShopService carRentalShopService;

    @GetMapping("/list")
    public String getCarsPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Car> carsList = this.carService.listAll();
        model.addAttribute("carsList", carsList);
        return "car/carsPage";
    }

    @GetMapping("/list/{id}")
    public String getCarsFromCarRentalShop(@RequestParam(required = false) String error, @PathVariable Long id,
        Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Car> carsList = this.carService.listCarsFromCarRentalShop(id);
        model.addAttribute("carsList", carsList);
        return "car/carsPage";
    }

    @PostMapping("/add")
    public String addCar(
        @RequestParam String name,
        @RequestParam Integer price,
        @RequestParam Integer year,
        @RequestParam Integer horsePower,
        @RequestParam String image,
        @RequestParam Long carRental

    ) {
        this.carService.addCar(name, price, year, horsePower, image, carRental);
        return "redirect:/cars/list";
    }

    @GetMapping("/addCar")
    public String showAddCarForm(Model theModel) {
        Car car = new Car();

        theModel.addAttribute("car", car);
        List<CarRentalShop> carRentalShops = this.carRentalShopService.listAll();
        theModel.addAttribute("carRentalShops", carRentalShops);
        return "car/add-car";
    }

    @GetMapping("/editCar")
    public String showEditCarForm(Model theModel) {

        Car car = new Car();

        theModel.addAttribute("car", car);
        List<CarRentalShop> carRentalShops = this.carRentalShopService.listAll();
        theModel.addAttribute("carRentalShops", carRentalShops);
        return "car/edit-car";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        this.carService.deleteById(id);
        return "redirect:/cars/list";
    }

    @GetMapping("/edit-form/{id}")
    public String editCar(@PathVariable Long id, Model model) {
        Car car = this.carService.findById(id).get();

        model.addAttribute("car", car);
        List<CarRentalShop> carRentalShops = this.carRentalShopService.listAll();
        model.addAttribute("carRentalShops", carRentalShops);
        return "car/edit-car";

    }

    @PostMapping("/add/{id}")
    public String updateCar(@PathVariable Long id,
        @RequestParam String name,
        @RequestParam Integer price,
        @RequestParam Integer year,
        @RequestParam Integer horsePower,
        @RequestParam String image,
        @RequestParam Long carRental
    ) {
        this.carService.update(id, name, price, year, horsePower, image, carRental);
        return "redirect:/cars/list";
    }

    @GetMapping("/reserve/{id}")
    public String reserveCar(@PathVariable Long id, Model model){
        Car car = this.carService.findById(id).get();
        model.addAttribute("car", car);
        return "reserve-car";
    }

    @PostMapping("reserve")
    public String reserveCar(@PathVariable Long id, @RequestParam String date){
        this.carService.calculatePrice(id, date);
        return "redirect:/shopping-cart";
    }
}

