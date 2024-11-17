package com.example.model;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String password_confirmation;

    // Constructor dengan password_confirmation
    public RegisterRequest(String name, String email, String password, String password_confirmation) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.password_confirmation = password_confirmation;  // Set password_confirmation dengan parameter
    }

    // Getter and Setter methods
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

    public String getPassword_confirmation() {
        return password_confirmation;  // Return password_confirmation, bukan password
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;  // Set dengan password_confirmation
    }
}
