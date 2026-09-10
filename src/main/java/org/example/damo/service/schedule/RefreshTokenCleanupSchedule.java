package org.example.damo.service.schedule;

import org.example.damo.service.security.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenCleanupSchedule {

    @Autowired
    private RefreshTokenService refreshTokenService;


    @Scheduled(cron = "0 0 0 * * *")
    public void cleanupExpiredTokensAndRevokeTokens() {
        refreshTokenService.deleteExpiredAndRevokeTokens();
    }
}
