package com.jhf.dto;


import jakarta.validation.constraints.NotNull;

public class UserLoginRequest {
    @NotNull
    private String username;

    @NotNull
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}