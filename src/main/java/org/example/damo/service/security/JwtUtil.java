package org.example.damo.service.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.example.damo.common.config.ApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Autowired
    private ApplicationConfiguration appConfig;

    private String secret;


    private Long expiration;


    @PostConstruct
    private void init() {
        secret = appConfig.getSecurity().getSecret();
        expiration = appConfig.getSecurity().getRefreshTokenExpiration();
    }

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


    public Boolean validateToken(String token, UserDetails userDetails) {
        Boolean isExpired = this.extractExpiration(token).before(new Date());
        String username = this.extractUsername(token);

        return !isExpired && username.equals(userDetails.getUsername());
    }


    public Date extractExpiration(String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = this.extractAllClaims(token);

        return claimsResolver.apply(claims);
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }
}
