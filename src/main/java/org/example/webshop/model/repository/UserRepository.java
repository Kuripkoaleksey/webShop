package org.example.webshop.model.repository;

import org.example.webshop.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);





    @Query("SELECT u FROM User u JOIN u.roles r WHERE r = :role")
    List<User> findByRoles(@Param("role") String role);

    User findByEmail(String email);
}

