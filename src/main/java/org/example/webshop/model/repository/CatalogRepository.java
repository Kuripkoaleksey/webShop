package org.example.webshop.model.repository;
import org.example.webshop.model.entities.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    // Здесь можно добавлять дополнительные методы запросов, если потребуется
}

