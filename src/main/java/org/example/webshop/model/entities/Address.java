package org.example.webshop.model.entities;

import java.util.regex.Pattern;
import jakarta.persistence.*;

//@Entity
//@Table(name = "Addresses")
//public class Address {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "Country", nullable = false, length = 50)
//    private String country;
//
//    @Column(name = "City", nullable = false, length = 50)
//    private String city;
//
//    @Column(name = "Street", nullable = false, length = 100)
//    private String street;
//
//    @Column(name = "Postal_code", nullable = false, length = 10)
//    private String postalCode;
//
//    @Column(name = "Building_number", nullable = false, length = 10)
//    private String buildingNumber;
@Entity
@Table(name = "Addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long address_id;

    @Column(name = "Country")
    private String country;

    @Column(name = "City")
    private String city;

    @Column(name = "Street")
    private String street;

    @Column(name = "Postal_code")
    private String postalCode;

    @Column(name = "Building_number")
    private String buildingNumber;

    // Геттеры и сеттеры


    // Регулярные выражения для валидации
    private static final String POSTAL_CODE_REGEX = "^[0-9]{5,6}$";
    private static final String BUILDING_NUMBER_REGEX = "^\\d+[A-Za-z]?\\d*$";
    private static final String CITY_REGEX = "^[A-Za-zА-Яа-яЁё\\s-]{2,50}$";
    private static final String COUNTRY_REGEX = "^[A-Za-zА-Яа-яЁё\\s-]{2,50}$";
    private static final String STREET_REGEX = "^[A-Za-zА-Яа-яЁё\\s-]{2,100}$";


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
    public Long getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Long address_id) {
        this.address_id = address_id;
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
        if (buildingNumber == null || buildingNumber.isEmpty() || !buildingNumber.matches("[a-zA-Z0-9\\s]+")) {
            throw new IllegalArgumentException("Invalid building number format.");
        }
        this.buildingNumber = buildingNumber;
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