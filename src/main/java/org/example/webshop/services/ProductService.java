package org.example.webshop.services;

import org.example.webshop.model.entities.Catalog;
import org.example.webshop.model.entities.Product;
import org.example.webshop.model.entities.User;
import org.example.webshop.model.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return Optional.ofNullable(productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found")));
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    /////////////////////////////////////////////////////


    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> sortProductsByName() {
        return productRepository.findAllByOrderByNameAsc();
    }

    public List<Product> sortProductsByPrice() {
        return productRepository.findAllByOrderByPriceAsc();
    }
    public List<Product> getProductsByCatalog(Catalog catalog) {
        return productRepository.findByCatalog(catalog);
    }
}