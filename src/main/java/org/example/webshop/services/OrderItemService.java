package org.example.webshop.services;


import org.example.webshop.model.entities.Order;
import org.example.webshop.model.entities.OrderItem;
import org.example.webshop.model.entities.Product;
import org.example.webshop.model.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
    public class OrderItemService {

        @Autowired
        private OrderItemRepository orderItemRepository;

        public OrderItem createOrderItem(Order order, Product product, int quantity, BigDecimal unitPrice) {
            OrderItem orderItem = new OrderItem(order, product, quantity, unitPrice);
            return orderItemRepository.save(orderItem);
        }

        public List<OrderItem> getOrderItemsByOrder(Order order) {
            return orderItemRepository.findByOrder(order);
        }
    }


