package org.example.webshop.services;

import org.example.webshop.model.entities.Redactor;
import org.example.webshop.model.repository.RedactorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RedactorService {

    private final RedactorRepository redactorRepository;

    @Autowired
    public RedactorService(RedactorRepository redactorRepository) {
        this.redactorRepository = redactorRepository;
    }

    // Метод для сохранения редактора
    public Redactor saveRedactor(Redactor redactor) {
        return redactorRepository.save(redactor);
    }

    // Метод для получения редактора по ID
    public Redactor getRedactorById(int redactorId) {
        return redactorRepository.findById(redactorId)
                .orElseThrow(() -> new IllegalArgumentException("Redactor not found"));
    }

    // Метод для обновления данных редактора
    public Redactor updateRedactor(Redactor updatedRedactor) {
        Optional<Redactor> existingRedactorOpt = redactorRepository.findById(updatedRedactor.getId());
        if (existingRedactorOpt.isPresent()) {
            Redactor existingRedactor = existingRedactorOpt.get();
            existingRedactor.setName(updatedRedactor.getName());
            existingRedactor.setEmail(updatedRedactor.getEmail());
            existingRedactor.setPassword(updatedRedactor.getPassword());
            return redactorRepository.save(existingRedactor);
        } else {
            throw new IllegalArgumentException("Redactor not found");
        }
    }


    // Метод для удаления редактора по ID
    public void deleteRedactor(int redactorId) {
        if (redactorRepository.existsById(redactorId)) {
            redactorRepository.deleteById(redactorId);
        } else {
            throw new IllegalArgumentException("Redactor not found");
        }
    }

    // Метод для получения всех редакторов
    public List<Redactor> getAllRedactors() {
        return (List<Redactor>) redactorRepository.findAll();
    }
}
