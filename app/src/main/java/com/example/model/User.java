package com.example.model;

public class User {
    private int id;           // ID pengguna
    private String name;      // Nama pengguna
    private String email;     // Email pengguna
    private String password;   // Tambahkan password

    public User() {}

    public User(String name, String email, String password) { // Constructor
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getter dan Setter
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // ... getter dan setter lainnya
}
