package com.project.ohl.domain.repository;

public final class AuthenticationException extends RuntimeException {
    private final int status;
    public AuthenticationException(int status) {
        super("Authentication request failed: " + status);
        this.status = status;
    }
    public int status() { return status; }
}
