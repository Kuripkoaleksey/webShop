package org.example.webshop.model.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//
//    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<CartItem> items = new ArrayList<>();

//    @OneToOne
//    @JoinColumn(name = "User_id", nullable = false)
//    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_id", referencedColumnName = "User_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> items = new ArrayList<>();
    // Constructors, getters, and setters

    public Cart() {}

    public Cart(User user) {
        this.user = user;
        user.setCart(this); // Establishing the bidirectional relationship
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public void addProduct(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().equals(product)) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(this, product, quantity));
    }

    public void removeProduct(Product product) {
        items.removeIf(item -> item.getProduct().equals(product));
    }

    public void clear() {
        items.clear();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (user.getCart() != this) {
            user.setCart(this); // Establishing the bidirectional relationship
        }
    }
}
