package org.example.webshop.controllers;

import org.example.webshop.model.entities.Admin;
import org.example.webshop.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Отображение формы для создания нового администратора
    @GetMapping("/new")
    public String showCreateAdminForm(Model model) {
        model.addAttribute("admin", new Admin());
        return "admins/create";
    }

    // Обработка формы создания нового администратора
    @PostMapping("/add")
    public String createAdmin(@ModelAttribute Admin admin) {
        adminService.saveAdmin(admin);
        return "redirect:/admin/admins";
    }

    // Отображение списка администраторов
    @GetMapping("/admins")
    public String listAdmins(Model model) {
        List<Admin> admins = adminService.getAllAdmins();
        model.addAttribute("admins", admins);
        return "admins/list";
    }

    // Отображение формы для редактирования администратора
    @GetMapping("/{id}/edit")
    public String showEditAdminForm(@PathVariable int id, Model model) {
        Admin admin = adminService.getAdminById(id);
        model.addAttribute("admin", admin);
        return "admins/edit";
    }

    // Обработка формы редактирования администратора
    @PostMapping("/{id}/edit")
    public String updateAdmin(@PathVariable int id, @ModelAttribute Admin admin) {
        if (admin.getPassword() == null || admin.getPassword().isEmpty()) {
            // Логика для случая отсутствия пароля, можно выбросить исключение или установить значение по умолчанию
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        admin.setId(id);
        adminService.updateAdmin(admin);
        return "redirect:/admin/admins";
    }

    @GetMapping("/{id}/confirm-delete")
    public String confirmDeleteAdmin(@PathVariable int id, Model model) {
        Admin admin = adminService.getAdminById(id);
        model.addAttribute("admin", admin);
        return "admins/confirm-delete";
    }

    // Удаление администратора
    @DeleteMapping("/{id}/delete")
    public String deleteAdmin(@PathVariable int id) {
        adminService.deleteAdmin(id);
        return "redirect:/admin/admins";
    }

}
