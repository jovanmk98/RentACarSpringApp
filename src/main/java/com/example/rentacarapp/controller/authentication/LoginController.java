package com.example.rentacarapp.controller.authentication;

import com.example.rentacarapp.model.User;
import com.example.rentacarapp.model.excep.InvalidInputException;
import com.example.rentacarapp.service.UserService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    @GetMapping
    public String getLoginPage() {
        return "login";
    }

    @PostMapping
    public String login(HttpServletRequest request, Model model) {
        User user;
        try {
            user = this.userService.login(request.getParameter("username"),
                request.getParameter("password"));
            request.getSession().setAttribute("user", user);
            return "redirect:/home";
        } catch (InvalidInputException exception) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            return "login";
        }
    }

}
