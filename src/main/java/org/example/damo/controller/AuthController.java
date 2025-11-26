package org.example.damo.controller;

import jakarta.validation.Valid;
import org.example.damo.dto.auth.AuthDto;
import org.example.damo.dto.auth.AuthResponseDto;
import org.example.damo.dto.auth.RefreshTokenDto;
import org.example.damo.dto.auth.RefreshTokenResponseDto;
import org.example.damo.dto.base.Response;
import org.example.damo.dto.user.UserDto;
import org.example.damo.service.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<Response> register(@Valid @RequestBody UserDto payload) {
       AuthResponseDto dto = authService.register(payload);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success("201" , "success" , "successfully registered user" , dto));
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@Valid @RequestBody AuthDto payload) {
        AuthResponseDto dto = authService.login(payload);

        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200" , "success" , "successfully logged" , dto));

    }

    @PostMapping("/refresh")
    public ResponseEntity<Response> refresh(@Valid @RequestBody RefreshTokenDto payload) {

        RefreshTokenResponseDto dto = authService.refreshToken(payload);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200" , "success" , "successfully refreshed token" , dto));
    }

}
