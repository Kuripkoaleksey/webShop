package org.example.webshop.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "Name", nullable = true, unique = false, length = 255)
    private String name;

    @Column(name = "Email", nullable = true, unique = true, length = 255)
    private String email;

    @Column(name = "Password", nullable = true, unique = true, length = 255)
    private String password;

    @Column(name = "Surname", nullable = true, unique = false, length = 255)
    private String surname;

    @Column(name = "phone_number", nullable = true, unique = true, length = 11)
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public Customer() {
        this.cart = new Cart();
        this.cart.setCustomer(this);
    }

    public Customer(String name, String email, String password, String surname, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.cart = new Cart();
        this.cart.setCustomer(this);
    }

    // Геттеры и сеттеры

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "ID=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
