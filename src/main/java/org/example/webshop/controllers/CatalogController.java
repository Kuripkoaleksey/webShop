package org.example.webshop.controllers;

import org.example.webshop.model.entities.Catalog;
import org.example.webshop.model.entities.Product;
import org.example.webshop.services.CatalogService;
import org.example.webshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/catalogs")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String listCatalogs(Model model) {
        model.addAttribute("catalogs", catalogService.getAllCatalogs());
        return "catalogs/list";
    }

    @GetMapping("/{id}")
    public String viewCatalog(@PathVariable Long id, Model model) {
        model.addAttribute("catalog", catalogService.getCatalogById(id).orElseThrow(() -> new RuntimeException("Catalog not found")));
        return "catalogs/view";
    }

    @GetMapping("/{id}/addProduct")
    public String showAddProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("catalogId", id);
        model.addAttribute("product", new Product());
        return "catalogs/addProduct";
    }

    @PostMapping("/{id}/addProduct")
    public String addProductToCatalog(@PathVariable Long id, @ModelAttribute Product product) {
        catalogService.addProductToCatalog(id, product);
        return "redirect:/catalogs/" + id;
    }
}

