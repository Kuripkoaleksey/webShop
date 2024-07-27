package org.example.webshop.controllers;

import org.example.webshop.model.entities.User;
import org.example.webshop.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/new")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/add")
    public String registerCustomer(@ModelAttribute User user, Model model) {
        try {
            registrationService.registerCustomer(user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getAddress());
            return "redirect:/login/form";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/redactor")
    public String showRedactorRegistrationForm(Model model, Authentication authentication) {
        if (isAdmin(authentication)) {
            model.addAttribute("user", new User());
            return "registerRedactor";
        } else {
            return "accessDenied"; // Страница с сообщением об отказе в доступе
        }
    }

    @PostMapping("/redactor")
    public String registerRedactor(@ModelAttribute User user, Model model, Authentication authentication) {
        if (isAdmin(authentication)) {
            try {
                registrationService.registerRedactor(user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getAddress());
                return "redirect:/admins/redactorsList";
            } catch (RuntimeException e) {
                model.addAttribute("error", e.getMessage());
                return "registerRedactor";
            }
        } else {
            return "accessDenied"; // Страница с сообщением об отказе в доступе
        }
    }

    private boolean isAdmin(Authentication authentication) {
        if (authentication != null) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            return authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
        }
        return false;
    }
}
