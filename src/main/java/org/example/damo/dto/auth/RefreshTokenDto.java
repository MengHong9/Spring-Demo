package org.example.damo.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class RefreshTokenDto {

    @JsonProperty("refresh_token")
    @NotNull(message = "refresh token is required")
    @NotEmpty(message = "refresh token should not be empty")
    String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
