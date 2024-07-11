package org.example.webshop.controllers;


import org.example.webshop.model.entities.Customer;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.example.webshop.model.entities.Redactor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.example.webshop.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
//import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    // Отображение списка покупателей
    @GetMapping("/customers")
    public String listCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customers/list";
    }

//    @GetMapping
//    public ResponseEntity<List<Customer>> getAllCustomers() {
//        List<Customer> customers = customerService.getAllCustomers();
//        return new ResponseEntity<>(customers, HttpStatus.OK);
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Customer> getCustomerById(@PathVariable long id) {
//        Optional<Customer> customer = customerService.getCustomerById(id);
//        return customer.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    // Отображение формы для создания нового покупателя
    @GetMapping("/new")
    public String showCreateCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customers/create";
    }

    // Обработка формы создания нового покупателя
    @PostMapping("/add")
    public String createCustomer(@ModelAttribute Customer customer) {
        customerService.saveCustomer(customer);
        return "redirect:/customer/customers";
    }

    // Отображение формы для редактирования покупателя
    @GetMapping("/{id}/edit")
    public String showEditCustomerForm(@PathVariable long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "customers/edit";
    }

    // Обработка формы редактирования покупателя
    @PostMapping("/{id}/edit")
    public String updateCustomer(@PathVariable long id, @ModelAttribute Customer customer) {
        if (customer.getPassword() == null || customer.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        customer.setId(id); // Обновляем ID клиента
        customerService.updateCustomer(customer);
        return "redirect:/customer/customers";
    }
//Форма подтверждения удаления покупателя
    @GetMapping("/{id}/confirm-delete")
    public String confirmDeleteCustomer(@PathVariable long id, Model model) {
       Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "customers/confirm-delete";
    }
    // Удаление покупателя
    @DeleteMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customer/customers";
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        Optional<Customer> customer = customerService.getCustomerByEmail(email);
        return customer.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<Customer> getCustomerByPhoneNumber(@PathVariable String phoneNumber) {
        Optional<Customer> customer = customerService.getCustomerByPhoneNumber(phoneNumber);
        return customer.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
