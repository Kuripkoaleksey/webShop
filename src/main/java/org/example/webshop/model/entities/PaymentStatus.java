package org.example.webshop.model.entities;

public enum PaymentStatus {
    PENDING,     // Ожидание оплаты
    COMPLETED,   // Оплата завершена
    FAILED,      // Оплата не удалась
    REFUNDED     // Оплата возвращена
}