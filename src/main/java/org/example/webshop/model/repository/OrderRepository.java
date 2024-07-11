package org.example.webshop.model.repository;

import org.example.webshop.model.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Дополнительные методы, если нужны
}
