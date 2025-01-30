package com.jhf.dto;

import jakarta.validation.constraints.NotNull;

public class UserPasswordUpdateRequest {
    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}