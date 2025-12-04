package org.example.damo.service.security;

import jakarta.annotation.PostConstruct;
import org.example.damo.common.config.ApplicationConfiguration;
import org.example.damo.entity.RefreshToken;
import org.example.damo.entity.User;
import org.example.damo.exception.model.ResourceNotFoundException;
import org.example.damo.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;


@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private ApplicationConfiguration appConfig;


    private Long expiration;

    @PostConstruct
    private void init() {
        this.expiration = appConfig.getSecurity().getRefreshTokenExpiration();
    }


    public RefreshToken createRefreshToken(User user) {
        String refreshToken = this.generateSecureRefreshToken();

        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setExpiresAt(LocalDateTime.now().plusHours(expiration));
        refreshTokenEntity.setUser(user);

        return refreshTokenRepository.save(refreshTokenEntity);
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(()-> new ResourceNotFoundException("Refresh token not found"));
    }

    public RefreshToken verifyRefreshToken(RefreshToken refreshToken) throws AuthenticationException {
        if (!refreshToken.isValid()) {
            refreshTokenRepository.delete(refreshToken);
            throw new AuthenticationException("Refresh token was expired or revoked");
        }

        return refreshToken;
    }

    public RefreshToken rotateRefreshToken(RefreshToken oldToken) {
        oldToken.setRevoked(true);
        refreshTokenRepository.save(oldToken);

        return this.createRefreshToken(oldToken.getUser());
    }

    private String generateSecureRefreshToken() {
        SecureRandom random = new SecureRandom();

        //array bytes of 64 length
        byte[] tokenBytes = new byte[64];

        //make each byte has its own secure value
        random.nextBytes(tokenBytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }


    public void deleteExpiredAndRevokeTokens() {
        refreshTokenRepository.deleteExpiredAndRevokeTokens(LocalDateTime.now());
    }
}
