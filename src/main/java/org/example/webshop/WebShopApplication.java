package org.example.webshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebShopApplication.class, args);
    }
}


//    private final AdminService adminService;
//
//    @Autowired
//    public WebShopApplication(AdminService adminService) {
//        this.adminService = adminService;
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(WebShopApplication.class, args);
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        // Получение списка всех администраторов
//        List<Admin> admins = adminService.getAllAdmins();
//
//        // Вывод списка администраторов на консоль
//        if (admins.isEmpty()) {
//            System.out.println("В системе нет администраторов.");
//        } else {
//            System.out.println("Список администраторов:");
//            for (Admin AdminController : admins) {
//                System.out.println(AdminController);
//            }

