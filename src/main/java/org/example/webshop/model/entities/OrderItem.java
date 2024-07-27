package org.example.webshop.model.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity
@Table(name = "OrderItems")  // Имя таблицы в базе данных
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "Product_id", nullable = false)
    private Product product;

    @Column(name = "Quantity", nullable = false)
    private int quantity;

    @Column(name = "Price", nullable = false)
    private BigDecimal price;
    // Конструкторы, геттеры и сеттеры

    public OrderItem() {}

    public OrderItem(Order order, Product product, int quantity, BigDecimal price) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}