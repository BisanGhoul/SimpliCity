package com.jhf.controller;

import com.jhf.dto.UserLoginRequest;
import com.jhf.dto.UserLoginResponse;
import com.jhf.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest loginRequest) {
        Authentication authentication = authenticationService.authenticate(loginRequest);
        if (authentication.isAuthenticated()) {
            UserLoginResponse responseData = authenticationService.generateAuthenticationDetails(authentication);
            return ResponseEntity.ok(responseData);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}