package com.codingwithset.ear.model;

public class User {
    String email;
    String full_name;

    public User(String email, String fullName) {
        this.email = email;
        this.full_name = fullName;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
