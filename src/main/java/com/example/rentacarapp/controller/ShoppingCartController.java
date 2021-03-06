package com.example.rentacarapp.controller;

import com.example.rentacarapp.model.Car;
import com.example.rentacarapp.model.ShoppingCart;
import com.example.rentacarapp.service.CarService;
import com.example.rentacarapp.service.ShoppingCartService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/shopping-cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final CarService carService;


    @GetMapping
    public String getShoppingCartPage(@RequestParam(required = false) String error,
                                      HttpServletRequest req,
                                      Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        String username = req.getRemoteUser();
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(username);
        model.addAttribute("product", this.shoppingCartService.listAllProducts(shoppingCart.getId()));
        return "shopping-cart";
    }

    @PostMapping("/add-product/{id}")
    public String addProductToShoppingCart(@PathVariable Long id, HttpServletRequest req) {
        try {
            String username = req.getRemoteUser();
            this.shoppingCartService.addProductToShoppingCart(username, id);
            return "redirect:/shopping-cart";
        } catch (RuntimeException exception) {
            return "redirect:/shopping-cart?error=" + exception.getMessage();
        }
    }
    @GetMapping("/add/date/{id}")
    public String showAddGear(@PathVariable Long id, Model model){
        model.addAttribute("id",id);
        return "select-date";
    }
    @PostMapping("/date/{id}")
    public String selectDate(@PathVariable Long id,
        @RequestParam(required = false) String dateFrom,
        @RequestParam(required = false) String dateTo,
        HttpServletRequest req){
        String username = req.getRemoteUser();
        Integer price = this.shoppingCartService.calculatePrice(id, dateFrom, dateTo);
        Car car = this.shoppingCartService.findById(id,username).get();
        car.setPrice(price);
        return "redirect:/shopping-cart";
    }
}
