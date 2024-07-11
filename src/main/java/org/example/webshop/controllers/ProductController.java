package org.example.webshop.controllers;

import org.example.webshop.model.entities.Product;
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

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

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
    public String viewProduct(@PathVariable long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "products/view";
    }

    @GetMapping("/new")
    public String showCreateProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/create";
    }

    @PostMapping("/new")
    public String createProduct(@ModelAttribute Product product,
                                @RequestParam("image") MultipartFile imageFile) throws IOException {
        // Handle image upload
        if (!imageFile.isEmpty()) {
            byte[] bytes = imageFile.getBytes();
            Path path = Paths.get("src/main/resources/static/images/" + imageFile.getOriginalFilename());
            Files.write(path, bytes);
            product.setImagePath("/images/" + imageFile.getOriginalFilename());
        }

        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String showEditProductForm(@PathVariable long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "products/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateProduct(@PathVariable long id, @ModelAttribute Product product) {
        product.setProductId(id);
        productService.updateProduct(product);
        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
