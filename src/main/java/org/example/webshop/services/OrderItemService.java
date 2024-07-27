package org.example.webshop.services;

import org.example.webshop.model.entities.*;
import org.example.webshop.model.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public OrderItem createOrderItem(Order order, Product product, int quantity, BigDecimal unitPrice) {
        OrderItem orderItem = new OrderItem();  // Используем стандартный конструктор
        orderItem.setOrder(order);              // Устанавливаем связь с объектом Order
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        orderItem.setPrice(unitPrice);          // Устанавливаем цену

        return orderItemRepository.save(orderItem);  // Сохраняем OrderItem
    }}
