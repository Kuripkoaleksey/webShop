package org.example.webshop.model.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_id")
    private Long userId;

    @Column(name = "Email", nullable = false, unique = true)
    private String email;


    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "First_name", nullable = false)
    private String firstName;

    @Column(name = "Last_name", nullable = false)
    private String lastName;

    @Column(name = "Phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "Address", nullable = false)
    private String address;

    @Column(name = "Created_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Transient
    private String confirmPassword;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "User_roles", joinColumns = @JoinColumn(name = "UserId"))
    @Column(name = "Role")
    private Collection<String> roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart cart;



    // Constructors, getters, and setters

    public User() {
        this.createdAt = LocalDateTime.now();
    }

    public User(String email, String passwordHash, String firstName, String lastName, String phoneNumber, String address, Collection<String> roles) {
        this.email = email;
        this.password = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.createdAt = LocalDateTime.now();
        this.roles = roles;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

//    public Cart getCart() {
//        return cart;
//    }
    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        if (cart.getUser() != this) {
            cart.setUser(this); // Establishing the bidirectional relationship
        }
    }

//    public User orElseThrow(Object userNotFound) {
//        return User;
//    }
}
