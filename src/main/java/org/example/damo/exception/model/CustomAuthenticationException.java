package org.example.damo.exception.model;

public class CustomAuthenticationException extends RuntimeException {
    public CustomAuthenticationException(String message) {
        super(message);
    }
}
