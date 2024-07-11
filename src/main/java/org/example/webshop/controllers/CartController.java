package org.example.webshop.controllers;

import org.example.webshop.model.entities.Cart;
import org.example.webshop.model.entities.Product;
import org.example.webshop.services.CartService;
import org.example.webshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping("/{cartId}")
    public String viewCart(@PathVariable long cartId, Model model) {
        Cart cart = cartService.getCartById(cartId);
        model.addAttribute("cart", cart);
        return "carts/view";
    }

    @PostMapping("/{cartId}/addProduct")
    public String addProductToCart(@PathVariable long cartId, @RequestParam long productId, @RequestParam int quantity) {
        Product product = productService.getProductById(productId); // Замените на ваш метод получения продукта
        Cart cart = cartService.getCartById(cartId);
        cartService.addProductToCart(cart, product, quantity);
        return "redirect:/carts/" + cartId;
    }

    @PostMapping("/{cartId}/removeProduct")
    public String removeProductFromCart(@PathVariable long cartId, @RequestParam long productId) {
        Product product = productService.getProductById(productId); // Замените на ваш метод получения продукта
        Cart cart = cartService.getCartById(cartId);
        cartService.removeProductFromCart(cart, product);
        return "redirect:/carts/" + cartId;
    }

    @GetMapping("/{cartId}/clear")
    public String clearCart(@PathVariable long cartId) {
        Cart cart = cartService.getCartById(cartId);
        cart.clear();
        cartService.saveCart(cart);
        return "redirect:/carts/" + cartId;
    }

    @GetMapping("/{cartId}/delete")
    public String showDeleteCartForm(@PathVariable long cartId, Model model) {
        Cart cart = cartService.getCartById(cartId);
        model.addAttribute("cart", cart);
        return "carts/confirm-delete";
    }

    @PostMapping("/{cartId}/delete")
    public String deleteCart(@PathVariable long cartId) {
        cartService.deleteCart(cartId);
        return "redirect:/customers"; // Перенаправление на список клиентов или другую страницу
    }

    // Добавление продукта в корзину
    @PostMapping("/addProduct")
    public String addProductToCart(@RequestParam("cartId") Long cartId,
                                   @RequestParam("productId") Long productId,
                                   @RequestParam("quantity") int quantity) {

        // Получаем корзину по её ID
        Cart cart = cartService.getCartById(cartId);

        // Получаем продукт по его ID
        Product product = productService.getProductById(productId);

        // Добавляем продукт в корзину
        cart.addProduct(product, quantity);

        // Сохраняем обновленную корзину
        cartService.updateCart(cart);

        // Перенаправляем пользователя на страницу корзины или другую страницу, например, на список продуктов
        return "redirect:/cart/" + cartId; // Пример перенаправления на страницу корзины
    }

}
