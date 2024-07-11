package org.example.webshop.model.repository;

import org.example.webshop.model.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
//Создадим интерфейс, который будет расширять JpaRepository для управления сущностью Address.
// Этот интерфейс автоматически будет содержать все базовые CRUD операции.
public interface AddressRepository extends JpaRepository<Address, Long> {
    // Дополнительные методы, если нужны
}
