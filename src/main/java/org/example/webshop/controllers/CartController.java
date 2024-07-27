package org.example.webshop.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.webshop.model.entities.*;
import org.example.webshop.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private OrderService orderService;

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;

    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity, Model model, HttpServletRequest request, Principal principal) {
        String csrfTokenFromRequest = request.getParameter("_csrf");
        String csrfTokenFromSession = (String) request.getSession().getAttribute("_csrf");
        System.out.println("CSRF token from request: " + csrfTokenFromRequest);
        System.out.println("CSRF token from session: " + csrfTokenFromSession);

        if (principal != null) {
            String loggedInUserName = principal.getName();
            User user = userService.findByUsername(loggedInUserName);
            if (user != null) {
                Product product = productService.findById(productId);
                if (product == null) {
                    model.addAttribute("error", "Товар не найден");
                    return "catalogs/catalog";
                }
                if (quantity <= 0) {
                    model.addAttribute("error", "Количество должно быть положительным");
                    return "catalogs/catalog";
                }
                if (quantity > product.getStock()) {
                    model.addAttribute("error", "Недостаточно доступных на складе запасов");
                    return "catalogs/catalog"; // Вернуться на каталог с сообщением об ошибке
                }
                cartService.addProductToCart(user.getUserId(), productId, quantity);
                return "redirect:/carts/cart?userId=" + user.getUserId();
            }
        }
        return "redirect:/login/form?error=true";
    }


    @GetMapping("/cart")
    public String viewCart(@RequestParam Long userId, Model model) {
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            List<CartItem> cartItems = cartService.getCartItems(userId);
            BigDecimal totalAmount = cartService.calculateTotalAmount(cartItems);
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("totalAmount", totalAmount);
            model.addAttribute("userId", userId);
            return "carts/cart"; // Убедитесь, что этот путь соответствует расположению вашего шаблона
        }
        return "redirect:/login/form?error=true";
    }



    @PostMapping("/update")
    public String updateCartItem(@RequestParam Long cartItemId, @RequestParam int quantity, Principal principal, Model model) {
        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            if (user != null) {
                CartItem cartItem = cartService.findCartItemById(cartItemId);
                if (cartItem != null) {
                    Product product = cartItem.getProduct();
                    if (quantity <= product.getStock()) {
                        cartService.updateCartItem(cartItemId, quantity);
                        return "redirect:/carts/cart?userId=" + user.getUserId();
                    } else {
                        model.addAttribute("error", "Недостаточно доступных на складе запасов");
                        return "carts/cart"; // Вернуться в корзину с сообщением об ошибке
                    }
                }
            }
        }
        return "redirect:/login/form?error=true";
    }


    @PostMapping("/remove")
    public String removeCartItem(@RequestParam Long cartItemId, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            if (user != null) {
                cartService.removeCartItem(cartItemId);
                return "redirect:/carts/cart?userId=" + user.getUserId();
            }
        }
        return "redirect:/login/form?error=true";
    }


    @PostMapping("/clear")
    public String clearCart(@RequestParam Long userId) {
        cartService.clearCart(userId);
        return "redirect:/carts?userId=" + userId;
    }


}

