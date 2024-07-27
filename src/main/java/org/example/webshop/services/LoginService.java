package org.example.webshop.services;

import org.example.webshop.model.entities.User;
import org.example.webshop.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean authenticateUser(String email, String password) {
//        email="111@mail.ru";
        User user = userRepository.findByEmail(email);
        if (user == null) {
            System.out.println("User not found with email CЕРВИС: " + email);
            return false;
        }
        boolean passwordsMatch = passwordEncoder.matches(password, user.getPassword());
        return passwordsMatch;
    }
}
