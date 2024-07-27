package org.example.webshop.model.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Catalogs")
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long catalogId;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Description")
    private String description;

    @OneToMany(mappedBy = "catalog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

    // Конструкторы, геттеры и сеттеры

    public Catalog() {}

    public Catalog(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
