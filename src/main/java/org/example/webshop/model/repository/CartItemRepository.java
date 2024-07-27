// CartItemRepository.java
package org.example.webshop.model.repository;

import org.example.webshop.model.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartUserUserId(Long userId);

    CartItem findByCartUserUserIdAndProductProductId(Long userId, Long productId);
}
