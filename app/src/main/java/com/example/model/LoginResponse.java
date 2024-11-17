package com.example.model;

public class LoginResponse {
    private String token;  // Field for the token
    private String message;  // Optional field for any response message

    // Constructor
    public LoginResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    // Getter and setter for the token
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Getter and setter for the message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
