package org.example.webshop.model.repository;

import org.example.webshop.model.entities.Order;
import org.example.webshop.model.entities.OrderItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderItemRepository extends CrudRepository<OrderItem,Long> {
    List<OrderItem> findByOrder(Order order);
}
