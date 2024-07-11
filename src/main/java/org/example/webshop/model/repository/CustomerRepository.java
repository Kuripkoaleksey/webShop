package org.example.webshop.model.repository;

import org.example.webshop.model.entities.Customer;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;


public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findByEmail(String email);
    Customer findByPhoneNumber(String phoneNumber);
}
