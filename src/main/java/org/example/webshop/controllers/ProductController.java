package org.example.webshop.controllers;

import org.example.webshop.model.entities.Catalog;
import org.example.webshop.model.entities.Product;
import org.example.webshop.services.CatalogService;
import org.example.webshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    @Autowired
    private CatalogService catalogService;


    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products/list";
    }

    @GetMapping("/{id}")
    public String viewProductDetails(@PathVariable long id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "products/details";
        } else {
            model.addAttribute("error", "Товар не найден");
            return "redirect:/products";
        }
    }

//    @GetMapping("/{id}")
//    public String viewProduct(@PathVariable long id, Model model) {
//        Optional<Product> product = productService.getProductById(id);
//        model.addAttribute("product", product);
//        return "products/view";
//    }

    // Показать форму для создания нового продукта
    @GetMapping("/new")
    public String showCreateProductForm(Model model) {
        model.addAttribute("product", new Product());
        List<Catalog> catalogs = catalogService.getAllCatalogs();
        model.addAttribute("catalogs", catalogs);
        return "products/create";
    }

    // Создание нового продукта
    @PostMapping("/new")
    public String createProduct(@ModelAttribute Product product,
                                @RequestParam("image") MultipartFile imageFile,
                                @RequestParam("catalogId") Long catalogId) throws IOException {
        // Обработка загрузки изображения
        if (!imageFile.isEmpty()) {
            byte[] bytes = imageFile.getBytes();
            Path path = Paths.get("src/main/resources/static/images/products/" + imageFile.getOriginalFilename());
            Files.write(path, bytes);
            product.setImagePath("/images/products/" + imageFile.getOriginalFilename());
        }

        // Установка каталога для продукта
        Catalog catalog = catalogService.getCatalogById(catalogId)
                .orElseThrow(() -> new RuntimeException("Catalog not found"));
        product.setCatalog(catalog);

        productService.saveProduct(product);
        return "redirect:/products";
    }


    @GetMapping("/{id}/edit")
    public String showEditProductForm(@PathVariable long id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            model.addAttribute("catalogs", catalogService.getAllCatalogs());
            return "products/edit";
        } else {
            model.addAttribute("error", "Product not found");
            return "redirect:/products";
        }
    }

    @PostMapping("/{id}/edit")
    public String updateProduct(@PathVariable long id,
                                @ModelAttribute Product product,
                                @RequestParam("catalogId") Long catalogId,
                                @RequestParam("image") MultipartFile imageFile) throws IOException {
        // Получаем текущий продукт из базы данных
        Product existingProduct = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Обработка загрузки изображения
        if (!imageFile.isEmpty()) {
            byte[] bytes = imageFile.getBytes();
            Path path = Paths.get("src/main/resources/static/images/products/" + imageFile.getOriginalFilename());
            Files.write(path, bytes);
            product.setImagePath("/images/products/" + imageFile.getOriginalFilename());
        } else {
            // Сохраняем старый путь к изображению, если новое изображение не загружается
            product.setImagePath(existingProduct.getImagePath());
        }

        // Установка каталога для продукта
        Catalog catalog = catalogService.getCatalogById(catalogId)
                .orElseThrow(() -> new RuntimeException("Catalog not found"));
        product.setCatalog(catalog);

        // Установка идентификатора продукта (чтобы избежать создания нового продукта)
        product.setProductId(id);

        // Обновление продукта
        productService.updateProduct(product);
        return "redirect:/products";
    }


    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }




}
