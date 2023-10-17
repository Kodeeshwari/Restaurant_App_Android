package com.example.admin_app.Models;

public class User {
    private String email;
    private String password;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email,String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and setters (you can generate these)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return getPassword();
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

