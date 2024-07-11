package org.example.webshop.controllers;

import org.example.webshop.model.entities.Redactor;
import org.example.webshop.services.RedactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/redactor")
public class RedactorController {

    private final RedactorService redactorService;

    @Autowired
    public RedactorController(RedactorService redactorService) {
        this.redactorService = redactorService;
    }

    // Отображение формы для создания нового редактора
    @GetMapping("/new")
    public String showCreateRedactorForm(Model model) {
        model.addAttribute("redactor", new Redactor());
        return "redactors/create";
    }

    // Обработка формы создания нового редактора
    @PostMapping("/add")
    public String createRedactor(@ModelAttribute Redactor redactor) {
        redactorService.saveRedactor(redactor);
        return "redirect:/redactor/redactors";
    }

    // Отображение списка редакторов
    @GetMapping("/redactors")
    public String listRedactors(Model model) {
        List<Redactor> redactors = redactorService.getAllRedactors();
        model.addAttribute("redactors", redactors);
        return "redactors/list";
    }

    // Отображение формы для редактирования редактора
    @GetMapping("/{id}/edit")
    public String showEditRedactorForm(@PathVariable int id, Model model) {
        Redactor redactor = redactorService.getRedactorById(id);
        model.addAttribute("redactor", redactor);
        return "redactors/edit";
    }

    // Обработка формы редактирования редактора
    @PostMapping("/{id}/edit")
    public String updateRedactor(@PathVariable int id, @ModelAttribute Redactor redactor) {
        if (redactor.getPassword() == null || redactor.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        redactor.setId(id);
        redactorService.updateRedactor(redactor);
        return "redirect:/redactor/redactors";
    }

//форма подтверждения удаления покупателя
    @GetMapping("/{id}/confirm-delete")
    public String confirmDeleteRedactor(@PathVariable int id, Model model) {
        Redactor redactor = redactorService.getRedactorById(id);
        model.addAttribute("redactor", redactor);
        return "redactors/confirm-delete";
    }

    // Удаление редактора
    @DeleteMapping("/{id}/delete")
    public String deleteRedactor(@PathVariable int id) {
        redactorService.deleteRedactor(id);
        return "redirect:/redactor/redactors";
    }

}
