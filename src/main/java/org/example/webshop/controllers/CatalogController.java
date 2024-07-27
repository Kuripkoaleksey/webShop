package org.example.webshop.controllers;

import org.example.webshop.model.entities.Catalog;
import org.example.webshop.model.entities.Product;
import org.example.webshop.model.entities.User;
import org.example.webshop.services.CatalogService;
import org.example.webshop.services.ProductService;
import org.example.webshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/catalogs")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @GetMapping("/list")
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
        return "redirect:/catalogs/list";
    }

    @GetMapping("/listCustomer")
    public String listCatalogsCustomer(Model model) {
        List<Catalog> catalogs = catalogService.getAllCatalogs();
        model.addAttribute("catalogs", catalogs);
        return "/catalogs/listCustomer"; // Возвращаем имя шаблона списка каталогов
    }

    @GetMapping("/{id}")
    public String viewCatalogCustomer(@PathVariable Long id, Model model,
                                      @RequestParam(required = false) String search,
                                      @RequestParam(required = false) String sortBy,
                                      Principal principal) {
        Catalog catalog = catalogService.getCatalogById(id).orElse(null);
        if (catalog == null) {
            model.addAttribute("error", "Каталог не найден");
            return "/catalogs/catalog"; // Возвращаем имя шаблона каталога
        }

        List<Product> products = productService.getProductsByCatalog(catalog);

        if (search != null && !search.isEmpty()) {
            products = products.stream()
                    .filter(product -> product.getName().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if ("name".equals(sortBy)) {
            products = products.stream()
                    .sorted(Comparator.comparing(Product::getName))
                    .collect(Collectors.toList());
        } else if ("price".equals(sortBy)) {
            products = products.stream()
                    .sorted(Comparator.comparing(Product::getPrice))
                    .collect(Collectors.toList());
        }

        model.addAttribute("catalog", catalog);
        model.addAttribute("products", products);

        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            if (user != null) {
                model.addAttribute("userId", user.getUserId());
            }
        }
        return "catalogs/catalog";
    }



    // Показать форму для редактирования существующего каталога
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Catalog> catalog = catalogService.getCatalogById(id);
        if (catalog.isPresent()) {
            model.addAttribute("catalog", catalog.get());
            return "/catalogs/editCatalog";
        } else {
            return "redirect:/catalogs/list";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateCatalog(@PathVariable Long id, @ModelAttribute Catalog catalog, RedirectAttributes redirectAttributes) {
        catalogService.updateCatalog(id, catalog);
        redirectAttributes.addFlashAttribute("message", "Каталог успешно обновлен!");
        return "redirect:/catalogs/list";
    }

    // Удалить существующий каталог

    // Показать форму удаления каталога
    @GetMapping("/delete/{id}")
    public String showDeleteForm(@RequestParam("id") Long id, Model model) {
        model.addAttribute("catalogId", id);
        return "catalogs/deleteCatalog";
    }

    // Обработка удаления каталога
    @DeleteMapping("/delete/{id}")
    public String deleteCatalog(@PathVariable Long id) {
        try {
            catalogService.deleteCatalog(id);
            return "redirect:/catalogs/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }}
