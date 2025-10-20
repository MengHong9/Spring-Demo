package org.example.damo.service.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${config.security.secret}")
    private String secret;

    @Value("${config.security.expiration}")
    private Long expiration;

    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId " , "12345UUID");

        return this.createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims , String subject) {
        String tok = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + this.expiration))
                .signWith(this.getKey(), SignatureAlgorithm.HS256)
                .compact();

        return tok;
    }
}
