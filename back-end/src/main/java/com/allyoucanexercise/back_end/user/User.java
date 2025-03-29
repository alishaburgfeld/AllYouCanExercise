package com.allyoucanexercise.back_end.user;

import jakarta.validation.constraints.NotEmpty;

public class User {
    private Integer id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
