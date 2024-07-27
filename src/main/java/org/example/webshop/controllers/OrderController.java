package org.example.webshop.controllers;

import org.example.webshop.exceptions.InsufficientStockException;
import org.example.webshop.model.entities.*;
import org.example.webshop.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @GetMapping("/confirmation")
    public ModelAndView showOrderConfirmation(@RequestParam Long userId) {
        List<CartItem> cartItems = cartService.getCartItems(userId);
        ModelAndView modelAndView = new ModelAndView("orders/confirmation");
        modelAndView.addObject("cartItems", cartItems);
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @PostMapping("/confirm")
    public ModelAndView confirmOrder(
            @RequestParam Long userId,
            @RequestParam String country,
            @RequestParam String city,
            @RequestParam String street,
            @RequestParam String postalCode,
            @RequestParam String buildingNumber
    ) {
        ModelAndView modelAndView = new ModelAndView("orders/confirmation");
        User user = userService.findById(userId).orElse(null);
        if (user == null) {
            modelAndView.addObject("error", "Пользователь не найден.");
            return modelAndView;
        }

        List<CartItem> cartItems = cartService.getCartItems(userId);
        if (cartItems.isEmpty()) {
            modelAndView.addObject("error", "Ваша корзина пуста.");
            return modelAndView;
        }

        Address address = new Address(country, city, street, postalCode, buildingNumber);
        Address savedAddress = addressService.save(address);

        if (savedAddress == null || savedAddress.getAddress_id() == null) {
            modelAndView.addObject("error", "Не удалось сохранить адрес.");
            return modelAndView;
        }

        try {
            Order order = orderService.createOrder(userId, cartItems, savedAddress);
            cartService.checkout(userId);
            modelAndView.addObject("order", order);
            modelAndView.addObject("orderItems", order.getOrderItems());
            modelAndView.addObject("totalAmount", order.getTotalAmount());
            return modelAndView;
        } catch (InsufficientStockException e) {
            modelAndView.addObject("error", e.getMessage());
        } catch (IllegalArgumentException e) {
            modelAndView.addObject("error", e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping("/checkout")
    public ModelAndView checkout(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);

            if (user != null) {
                Long userId = user.getUserId();
                List<CartItem> cartItems = cartService.getCartItems(userId);

                if (cartItems.isEmpty()) {
                    modelAndView.setViewName("carts/cart");
                    modelAndView.addObject("error", "Ваша корзина пуста. Пожалуйста добавьте товары перед подтверждением заказа.");
                    return modelAndView;
                }

                modelAndView.setViewName("orders/checkout");
                modelAndView.addObject("userId", userId);
                return modelAndView;
            } else {
                modelAndView.setViewName("carts/cart");
                modelAndView.addObject("error", "Пользователь не найден.");
                return modelAndView;
            }
        }
        modelAndView.setViewName("redirect:/login/form?error=true");
        return modelAndView;
    }

    @PostMapping("/pay")
    public ModelAndView payOrder(@RequestParam Long orderId) {
        ModelAndView modelAndView = new ModelAndView();
        Order order = orderService.findById(orderId).orElse(null);

        if (order != null) {
            order.setOrderStatus(OrderStatus.PROCESSING);
            order.setPaymentStatus(PaymentStatus.PAID);
            orderService.save(order);

            modelAndView.setViewName("orders/paymentConfirmation");
            modelAndView.addObject("message", "Ваш заказ оплачен. Наш менеджер свяжется с вами для согласования деталей доставки.");
            return modelAndView;
        } else {
            modelAndView.setViewName("orders/confirmation");
            modelAndView.addObject("error", "Заказ не найден.");
            return modelAndView;
        }
    }
}
