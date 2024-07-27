package org.example.webshop.model.repository;

import org.example.webshop.model.entities.Cart;
import org.example.webshop.model.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserUserId(Long userId);
}