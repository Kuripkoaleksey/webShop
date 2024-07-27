package org.example.webshop.services;

import org.example.webshop.exceptions.InsufficientStockException;
import org.example.webshop.model.entities.*;
import org.example.webshop.model.repository.OrderItemRepository;
import org.example.webshop.model.repository.OrderRepository;
import org.example.webshop.model.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Order createOrder(Long userId, List<CartItem> cartItems, Address address) {
        LocalDateTime orderDate = LocalDateTime.now();
        BigDecimal totalAmount = cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUserId(userId);
        order.setOrderDate(orderDate);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setPaymentMethod("Credit Card");
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setTotalAmount(totalAmount);
        order.setAddress_id(address.getAddress_id());
        order.setEstimatedDeliveryDate(null);

        // Сначала сохраняем объект Order, чтобы получить его ID
        order = orderRepository.save(order);

        // Обновляем количество товаров
        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            if (product.getStock() < item.getQuantity()) {
                throw new InsufficientStockException("Недостаточно товара: " + product.getName());
            }
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }

        Order finalOrder = order;
        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(finalOrder);
                    orderItem.setProduct(item.getProduct());
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setPrice(item.getProduct().getPrice());
                    return orderItemRepository.save(orderItem);
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);

        return orderRepository.save(order);
    }

    public Optional<Order> findById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
