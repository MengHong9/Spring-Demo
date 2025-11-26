package org.example.damo.service.security;

import org.example.damo.entity.RefreshToken;
import org.example.damo.entity.User;
import org.example.damo.exception.model.ResourceNotFoundException;
import org.example.damo.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(User user) {

        // TODO : generate as Base64 URL Encoder
        String refreshToken = UUID.randomUUID().toString();

        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setExpiresAt(LocalDateTime.now().plusHours(3));
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
}
