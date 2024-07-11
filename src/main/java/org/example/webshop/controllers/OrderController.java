package org.example.webshop.controllers;
import org.example.webshop.model.entities.Address;
import org.example.webshop.model.entities.Order;
import org.example.webshop.model.entities.OrderStatus;
import org.example.webshop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;


@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Метод для создания заказа с адресом
    @PostMapping("/create")
    public Order createOrder(@RequestBody Address address) {
        Date orderDate = new Date();
        return orderService.createOrder(address, orderDate);
    }
    // Метод для изменения статуса заказа
    @PatchMapping("/{orderId}/status")
    public Order updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus newStatus) {
        return orderService.updateOrderStatus(orderId, newStatus);
    }
    // Метод для получения информации о заказе
    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }
}
