package org.example.webshop.controllers;

import org.example.webshop.model.entities.User;
import org.example.webshop.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class AuthController {

    private final LoginService loginService;

    @Autowired
    public AuthController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/form")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/enter")
    public String loginSubmit(@RequestParam("username") String email, @RequestParam("password") String password, Model model) {
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        try {
            boolean authenticated = loginService.authenticateUser(email, password);
            if (authenticated) {
                return "redirect:/catalogs/listCustomer";
            } else {
                model.addAttribute("error", "Неверный email или password");
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Произошла внутренняя ошибка. Пожалуйста, повторите попытку позже.");
            return "login";
        }
    }
}
