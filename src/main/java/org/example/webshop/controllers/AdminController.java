package org.example.webshop.controllers;

import org.example.webshop.model.entities.User;
import org.example.webshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admins")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/redactorsList")
    public String listRedactors(Model model) {
        model.addAttribute("redactors", userService.getAllRedactors());
        return "admins/redactorsList";
    }

    @GetMapping("/editRedactor/{userId}")
    public String showEditForm(@PathVariable("userId") Long userId, Model model) {
        User user = userService.getUserById(userId);
        if (user == null) {
            model.addAttribute("error", "Пользователь не найден");
            return "error";
        }
        model.addAttribute("user", user);
        return "admins/editRedactor";
    }

    @PostMapping("/editRedactor")
    public String editRedactor(@ModelAttribute User user, Model model) {
        System.out.println("User ID: " + user.getUserId()); // Логируем userId для отладки
        try {
            if (user.getUserId() == null) {
                throw new IllegalArgumentException("User ID не должен быть пустым");
            }
            userService.updateUser(user);
            return "redirect:/admins/redactorsList";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);  // Возвращаем пользователя, чтобы данные не потерялись
            model.addAttribute("error", "Не удалось обновить данные пользователя");
            return "admins/editRedactor";
        }
    }


    @PostMapping("/deleteRedactor/{userId}")
    public String deleteRedactor(@PathVariable("userId") Long userId) {
        try {
            userService.deleteUser(userId);
            return "redirect:/admins/redactorsList";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }
}
