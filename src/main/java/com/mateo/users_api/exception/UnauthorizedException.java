package com.mateo.users_api.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Invalid credentials");
    }
}