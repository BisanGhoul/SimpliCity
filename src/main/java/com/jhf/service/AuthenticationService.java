package com.jhf.service;

import com.jhf.dto.UserBasicInfoDTO;
import com.jhf.dto.UserLoginRequest;
import com.jhf.dto.UserLoginResponse;
import com.jhf.models.User;
import com.jhf.security.AuthenticationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final AuthenticationTokenService tokenService;
    private final UserService userService;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, AuthenticationTokenService tokenService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    public Authentication authenticate(UserLoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    public UserLoginResponse generateAuthenticationDetails(Authentication authentication) {
        UserLoginResponse responseData = new UserLoginResponse();
        responseData.setUser(userService.convertToDTO((User) authentication.getPrincipal()));
        String token = tokenService.generateToken(authentication);
        responseData.setToken(token);
        return responseData;
    }
}