package com.jhf.dto;


import com.jhf.models.User;

public class UserLoginResponse {
    private UserBasicInfoDTO user;
    private String token;

    public UserBasicInfoDTO getUser() {
        return user;
    }

    public void setUser(UserBasicInfoDTO user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}