package org.example.webshop.model.repository;

import org.example.webshop.model.entities.Catalog;
import org.example.webshop.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findAllByOrderByNameAsc();
    List<Product> findAllByOrderByPriceAsc();
    List<Product> findByCatalog(Catalog catalog);
}
