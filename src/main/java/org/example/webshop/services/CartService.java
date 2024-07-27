package org.example.webshop.services;

import jakarta.transaction.Transactional;
import org.example.webshop.model.entities.Cart;
import org.example.webshop.model.entities.CartItem;
import org.example.webshop.model.entities.Product;
import org.example.webshop.model.entities.User;
import org.example.webshop.model.repository.CartItemRepository;
import org.example.webshop.model.repository.CartRepository;
import org.example.webshop.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Transactional
    public void addProductToCart(Long userId, Long productId, int quantity) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productService.findById(productId);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            user.setCart(cart);
        }

        CartItem existingCartItem = cartItemRepository.findByCartUserUserIdAndProductProductId(userId, productId);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            cartItemRepository.save(newCartItem);
        }
    }

//    public List<CartItem> getCartItems(Long userId) {
//        return cartItemRepository.findByCartUserUserId(userId);
//    }

    public void updateCartItem(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("CartItem not found"));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }


    public void removeCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));
        cartItemRepository.delete(cartItem);
    }

    public void clearCart(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByCartUserUserId(userId);
        cartItemRepository.deleteAll(cartItems);
    }

    public void checkout(Long userId) {
        clearCart(userId);
    }

    public CartItem findCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId).orElse(null);
    }
//    public BigDecimal calculateTotalAmount(List<CartItem> cartItems) {
//        return cartItems.stream()
//                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }

    public BigDecimal calculateTotalAmount(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<CartItem> getCartItems(Long userId) {
        Optional<Cart> cart = Optional.ofNullable(cartRepository.findByUserUserId(userId));
        return cart.map(Cart::getItems).orElse(Collections.emptyList());
    }
}
