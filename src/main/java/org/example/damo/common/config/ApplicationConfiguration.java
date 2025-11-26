package org.example.damo.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;



@Configuration
@ConfigurationProperties(prefix = "config")
public class ApplicationConfiguration {
    private Security security;

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }


    public static class Security {
        private String secret;
        private Long expiration;
        private Long refreshTokenExpiration;

        public String getSecret() {
            return secret;
        }
        public void setSecret(String secret) {
            this.secret = secret;
        }
        public Long getExpiration() {
            return expiration;
        }
        public void setExpiration(Long expiration) {
            this.expiration = expiration;
        }
        public Long getRefreshTokenExpiration() {
            return refreshTokenExpiration;
        }
        public void setRefreshTokenExpiration(Long refreshTokenExpiration) {
            this.refreshTokenExpiration = refreshTokenExpiration;
        }
    }
}
