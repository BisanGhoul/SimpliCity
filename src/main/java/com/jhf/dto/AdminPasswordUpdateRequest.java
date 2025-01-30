package com.jhf.dto;


import jakarta.validation.constraints.NotNull;

public class AdminPasswordUpdateRequest {
    @NotNull
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }
}