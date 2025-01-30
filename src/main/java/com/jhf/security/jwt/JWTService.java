package com.jhf.security.jwt;

import com.jhf.security.AuthenticationTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JWTService implements AuthenticationTokenService {
    private static final String SECRET = "ce678705a03c86d81944833b9779cceba559f20da7ef1127887b426df3f796cd";
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    private static final long TOKEN_EXPIRATION_TIME = 24 * 60 * 60 * 1000L;

    public String generateToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("roles", authentication.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token, String username) {
        Claims body = getClaimsFromToken(token);
        String tokenUsername = body.getSubject();
        boolean notExpired = body.getExpiration().after(new Date());
        return username.equals(tokenUsername) && notExpired;
    }

    public String extractUsername(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
