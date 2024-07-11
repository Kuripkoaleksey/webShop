package org.example.webshop.services;

import org.example.webshop.model.entities.Cart;
import org.example.webshop.model.entities.Product;
import org.example.webshop.model.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public void addProductToCart(Cart cart, Product product, int quantity) {
        cart.addProduct(product, quantity);
        cartRepository.save(cart);
    }

    public void removeProductFromCart(Cart cart, Product product) {
        cart.removeProduct(product);
        cartRepository.save(cart);
    }

    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    public void updateCart(Cart cart) {
        cartRepository.save(cart);
    }
}

