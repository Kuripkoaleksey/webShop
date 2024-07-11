package org.example.webshop.model.entities;

import java.util.regex.Pattern;

import jakarta.persistence.*;

@Entity
@Table(name = "Addresses") // Указываем имя таблицы, с которой связывается класс
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Указываем автоинкрементируемый первичный ключ
    private Long id; // Поле для идентификатора в таблице

    @Column(name = "country", nullable = false, length = 50) // Указываем имя столбца и ограничения
    private String country;

    @Column(name = "city", nullable = false, length = 50) // Указываем имя столбца и ограничения
    private String city;

    @Column(name = "street", nullable = false, length = 100) // Указываем имя столбца и ограничения
    private String street;

    @Column(name = "postal_code", nullable = false, length = 10) // Указываем имя столбца и ограничения
    private String postalCode;

    @Column(name = "building_number", nullable = false, length = 10) // Указываем имя столбца и ограничения
    private String buildingNumber;

    // Регулярные выражения для валидации
    private static final String POSTAL_CODE_REGEX = "^[0-9]{5,6}$"; // Пример для почтового индекса (5 или 6 цифр)
    private static final String BUILDING_NUMBER_REGEX = "^\\d+[A-Za-z]?\\d*$"; // Пример для номера здания (цифры с опциональными буквами)
    private static final String CITY_REGEX = "^[A-Za-zА-Яа-яЁё\\s-]{2,50}$"; // Пример для города (буквы, пробелы и дефисы)
    private static final String COUNTRY_REGEX = "^[A-Za-zА-Яа-яЁё\\s-]{2,50}$"; // Пример для страны (буквы, пробелы и дефисы)
    private static final String STREET_REGEX = "^[A-Za-zА-Яа-яЁё\\s-]{2,100}$"; // Пример для улицы (буквы, пробелы и дефисы)

    // Конструкторы
    public Address() {}

    public Address(String country, String city, String street, String postalCode, String buildingNumber) {
        setCountry(country);
        setCity(city);
        setStreet(street);
        setPostalCode(postalCode);
        setBuildingNumber(buildingNumber);
    }

    // Геттеры и сеттеры с валидацией
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        if (isValid(country, COUNTRY_REGEX)) {
            this.country = country;
        } else {
            throw new IllegalArgumentException("Invalid country format.");
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (isValid(city, CITY_REGEX)) {
            this.city = city;
        } else {
            throw new IllegalArgumentException("Invalid city format.");
        }
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        if (isValid(street, STREET_REGEX)) {
            this.street = street;
        } else {
            throw new IllegalArgumentException("Invalid street format.");
        }
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        if (isValid(postalCode, POSTAL_CODE_REGEX)) {
            this.postalCode = postalCode;
        } else {
            throw new IllegalArgumentException("Invalid postal code format.");
        }
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        if (isValid(buildingNumber, BUILDING_NUMBER_REGEX)) {
            this.buildingNumber = buildingNumber;
        } else {
            throw new IllegalArgumentException("Invalid building number format.");
        }
    }

    // Метод для проверки соответствия строки регулярному выражению
    private boolean isValid(String value, String regex) {
        return Pattern.matches(regex, value);
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s", buildingNumber, street, city, postalCode, country);
    }
}
