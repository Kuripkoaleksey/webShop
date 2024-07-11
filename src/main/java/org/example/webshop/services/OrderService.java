package org.example.webshop.services;
import org.example.webshop.model.entities.*;
import org.example.webshop.model.repository.AddressRepository;
import org.example.webshop.model.repository.OrderItemRepository;
import org.example.webshop.model.repository.OrderRepository;
import org.example.webshop.model.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    // Метод для создания и сохранения заказа с адресом
    public Order createOrder(Address address, Date orderDate) {
        // Сначала сохраняем адрес
        address = addressRepository.save(address);

            // Создаем заказ со статусом PENDING
            Order order = new Order(address, orderDate, OrderStatus.PENDING);
            return orderRepository.save(order);
    }

    // Метод для добавления товара к заказу
    public OrderItem addOrderItem(Order order, Long productID, int quantity) {
        // Получаем продукт по ID
        Product product = productRepository.findById(productID)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));

        // Определяем цену за единицу
        BigDecimal unitPrice = product.getPrice();

        // Создаем и сохраняем OrderItem
        OrderItem orderItem = new OrderItem(order, product, quantity, unitPrice);
        return orderItemRepository.save(orderItem);
    }

    // Метод для получения товаров по заказу
    public List<OrderItem> getOrderItemsByOrder(Order order) {
        return orderItemRepository.findByOrder(order);
    }

    // Метод для получения заказа по ID
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));
    }
    // Метод для изменения статуса заказа
    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = getOrderById(orderId);
        order.setOrderStatus(newStatus);
        return orderRepository.save(order);
    }
}
