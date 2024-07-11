package org.example.webshop;

import org.example.webshop.model.entities.Admin;
import org.example.webshop.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class WebShopApplication implements CommandLineRunner {

    private final AdminService adminService;

    @Autowired
    public WebShopApplication(AdminService adminService) {
        this.adminService = adminService;
    }

    public static void main(String[] args) {
        SpringApplication.run(WebShopApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Получение списка всех администраторов
        List<Admin> admins = adminService.getAllAdmins();

        // Вывод списка администраторов на консоль
        if (admins.isEmpty()) {
            System.out.println("В системе нет администраторов.");
        } else {
            System.out.println("Список администраторов:");
            for (Admin admin : admins) {
                System.out.println(admin);
            }
        }
    }
}
