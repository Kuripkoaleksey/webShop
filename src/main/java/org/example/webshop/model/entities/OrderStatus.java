package org.example.webshop.model.entities;

public enum OrderStatus {
    PENDING,     // Заказ ожидает обработки
    PROCESSING,  // Заказ в обработке
    COMPLETED,   // Заказ выполнен
    CANCELED     // Заказ отменен
}

