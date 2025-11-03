package org.example.damo.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AuthDto {

    @NotNull(message = "username is required!")
    @NotBlank(message = "username must not be blank")
    private String username;

    @NotNull(message = "password is required!")
    @NotBlank(message = "password must not be blank")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
