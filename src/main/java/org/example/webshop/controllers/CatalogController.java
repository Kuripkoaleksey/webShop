package org.example.webshop.controllers;

import org.example.webshop.model.entities.Catalog;
import org.example.webshop.model.entities.Product;
import org.example.webshop.services.CatalogService;
import org.example.webshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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

    @GetMapping("/new")
    public String showCreateCatalogForm(Model model) {
        model.addAttribute("catalog", new Catalog());
        return "catalogs/create";
    }

    @PostMapping("/new")
    public String createCatalog(@ModelAttribute Catalog catalog) {
        catalogService.createCatalog(catalog);
        return "redirect:/catalogs";
    }

    @GetMapping("/{id}/addProduct")
    public String showAddProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("catalogId", id);
        model.addAttribute("product", new Product());
        return "catalogs/addProduct";
    }
// Этот метод работает
//    @PostMapping("/{id}/addProduct")
//    public String addProductToCatalog(@PathVariable Long id, @ModelAttribute Product product) {
//        catalogService.addProductToCatalog(id, product);
//        return "redirect:/catalogs/" + id;
//    }
/////////////////////////////////
@PostMapping("/{id}/addProduct")
public String addProductToCatalog(@PathVariable Long id, @ModelAttribute Product product,
                                  @RequestParam("image") MultipartFile imageFile) throws IOException {
    if (!imageFile.isEmpty()) {
        // Чистый путь к файлу для избежания ошибок
        String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());

        // Убедитесь, что директория существует
        Path uploadPath = Paths.get("src/main/resources/static/images/products/");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Запись файла
        Path filePath = uploadPath.resolve(fileName);
        try (InputStream inputStream = imageFile.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        // Установка пути к изображению в продукте
        product.setImagePath("/images/products/" + fileName);
    }

    catalogService.addProductToCatalog(id, product);
    return "redirect:/catalogs/" + id;
}

//    @PostMapping("/{id}/addProduct")
//    public String addProductToCatalog(@PathVariable Long id, @ModelAttribute Product product,
//                                      @RequestParam("image") MultipartFile imageFile) throws IOException {
//        if (!imageFile.isEmpty()) {
//            byte[] bytes = imageFile.getBytes();
//            String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename()); // Чистый путь к файлу, чтобы избежать ошибок
//            Path path = Paths.get("src/main/resources/static/images/products/" + fileName);
//            Files.write(path, bytes);
//            product.setImagePath("/images/products/" + fileName); // Исправленный путь к изображению
//        }
//
//        catalogService.addProductToCatalog(id, product);
//        return "redirect:/catalogs/" + id;
//    }


//    @GetMapping("/{id}")
//    public String listCatalogs(@PathVariable Long id,Model model) {
//        model.addAttribute("catalogs", catalogService.getAllCatalogs());
//        return "catalogs/list";
//    }

    @GetMapping("/{id}")
    public String viewCatalog(@PathVariable Long id, Model model) {
        Catalog catalog = catalogService.getCatalogById(id).orElseThrow(() -> new RuntimeException("Catalog not found"));
        model.addAttribute("catalog", catalog);
        model.addAttribute("products", catalog.getProducts());
        return "catalogs/view";
    }


}

