package org.example.webshop.services;

import org.example.webshop.model.entities.Customer;
import org.example.webshop.model.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    public Customer getCustomerById(long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Redactor not found"));
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    // Метод для сохранения покупателя
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    public Customer updateCustomer  (Customer updatedCustomer) {
        Optional<Customer> existingCustomerOpt = customerRepository.findById(updatedCustomer.getId());
        if (existingCustomerOpt.isPresent()) {
            Customer existingCustomer = existingCustomerOpt.get();
            existingCustomer.setName(updatedCustomer.getName());
            existingCustomer.setSurname(updatedCustomer.getSurname());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setPassword(updatedCustomer.getPassword());
            existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
            return customerRepository.save(existingCustomer);
        } else {
            throw new IllegalArgumentException("Customer not found");
        }
    }
    public void deleteCustomer(long customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
        } else {
            throw new IllegalArgumentException("Customer not found");
        }
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return Optional.ofNullable(customerRepository.findByEmail(email));
    }

    public Optional<Customer> getCustomerByPhoneNumber(String phoneNumber) {
        return Optional.ofNullable(customerRepository.findByPhoneNumber(phoneNumber));
    }
}
