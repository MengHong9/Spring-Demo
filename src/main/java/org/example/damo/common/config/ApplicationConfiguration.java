package org.example.damo.common.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;


@Configuration
@ConfigurationProperties(prefix = "config")
public class ApplicationConfiguration {
    private Security security;
    private Pagination pagination;

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public Pagination getPagination() {
        return pagination;
    }
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
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



    public static class Pagination {
        private String baseUrl;
        private HashMap<String , String> uri;

        public String getBaseUrl() {
            return baseUrl;
        }
        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }
        public HashMap<String , String> getUri() {
            return uri;
        }
        public void setUri(HashMap<String , String> uri) {
            this.uri = uri;
        }




        public String getUrlByResource(String resource) {
            return baseUrl.concat(uri.getOrDefault(resource, ""));
        }
    }
}
