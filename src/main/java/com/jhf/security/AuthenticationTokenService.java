package com.jhf.security;
import org.springframework.security.core.Authentication;
public interface  AuthenticationTokenService {
    String generateToken(Authentication authentication);

    boolean validateToken(String username, String token);

    String extractUsername(String token);
}
