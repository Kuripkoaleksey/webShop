package org.example.webshop.services;

import org.example.webshop.model.entities.Catalog;
//import org.example.webshop.repositories.CatalogRepository;
import org.example.webshop.model.entities.Product;
import org.example.webshop.model.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    public List<Catalog> getAllCatalogs() {
        return catalogRepository.findAll();
    }

    public Optional<Catalog> getCatalogById(Long id) {
        return catalogRepository.findById(id);
    }

    public Catalog createOrUpdateCatalog(Catalog catalog) {
        return catalogRepository.save(catalog);
    }

    public void deleteCatalog(Long id) {
        catalogRepository.deleteById(id);
    }

    public Catalog addProductToCatalog(Long catalogId, Product product) {
        Optional<Catalog> catalogOptional = catalogRepository.findById(catalogId);
        if (catalogOptional.isPresent()) {
            Catalog catalog = catalogOptional.get();
            product.setCatalog(catalog);
            catalog.getProducts().add(product);
            return catalogRepository.save(catalog);
        } else {
            throw new RuntimeException("Catalog not found");
        }
    }
}

