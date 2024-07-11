package org.example.webshop.services;

import org.example.webshop.model.entities.Admin;
import org.example.webshop.model.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    // Метод для сохранения администратора
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    // Метод для получения администратора по ID
    public Admin getAdminById(int adminId) {
        return adminRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid admin ID"));
    }

    // Метод для обновления данных администратора
    public Admin updateAdmin(Admin updatedAdmin) {
        Optional<Admin> existingAdminOpt = adminRepository.findById(updatedAdmin.getId());
        if (existingAdminOpt.isPresent()) {
            Admin existingAdmin = existingAdminOpt.get();
            existingAdmin.setName(updatedAdmin.getName());
            existingAdmin.setEmail(updatedAdmin.getEmail());
            existingAdmin.setPassword(updatedAdmin.getPassword());
            return adminRepository.save(existingAdmin);
        } else {
            throw new IllegalArgumentException("Admin not found");
        }
    }

    // Метод для удаления администратора по ID
    public void deleteAdmin(int adminId) {
        if (adminRepository.existsById(adminId)) {
            adminRepository.deleteById(adminId);
        } else {
            throw new IllegalArgumentException("Invalid admin ID");
        }
    }

    // Метод для получения всех администраторов
    public List<Admin> getAllAdmins() {
        return (List<Admin>) adminRepository.findAll();
    }
}
