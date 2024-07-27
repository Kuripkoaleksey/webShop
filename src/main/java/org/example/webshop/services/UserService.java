package org.example.webshop.services;

import org.example.webshop.model.entities.User;
import org.example.webshop.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//import java.util.Optional;

//import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public User save(User user) {
        return userRepository.save(user);
    }


    public User findByUsername(String username) {
        return userRepository.findByEmail(username); // Предположим, что email используется как username
    }

    public void updateUser(User user) {
        Optional<User> existingUser = userRepository.findById(user.getUserId());
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPassword(user.getPassword());
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setPhoneNumber(user.getPhoneNumber());
            updatedUser.setAddress(user.getAddress());
            userRepository.save(updatedUser);
        } else {
            throw new RuntimeException("User not found");
        }}

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
//
//    public List<User> getAllRedactors() {
//        return userRepository.findByRole("REDACTOR");
//    }
public List<User> getAllRedactors() {
    return userRepository.findByRoles("REDACTOR");
}
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }


    }

