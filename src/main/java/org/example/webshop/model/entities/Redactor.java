package org.example.webshop.model.entities;

import jakarta.persistence.*;



    @Entity
    @Table(name = "Redactors")
    public class Redactor {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(name = "Name", nullable = true, unique = true, length = 255)
        private String name;

        @Column(name = "Email", nullable = true, unique = true, length = 255)
        private String email;

        @Column(name = "Password", nullable = true, unique = true, length = 255)
        private String password;

        public Redactor() {
        }

        public Redactor(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }

        // Геттеры и сеттеры
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "Admin{" +
                    "ID=" + id +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }