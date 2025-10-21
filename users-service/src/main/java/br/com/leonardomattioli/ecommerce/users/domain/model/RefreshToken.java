package br.com.leonardomattioli.ecommerce.users.domain.model;

import java.time.Instant;
import java.util.UUID;

public class RefreshToken {
    private UUID id;
    private User user;
    private String token;
    private boolean revoked;
    private Instant expiresAt;

    public RefreshToken() {
    }

    public RefreshToken(UUID id, User user, String token, boolean revoked, Instant expiresAt) {
        this.id = id;
        this.user = user;
        this.token = token;
        this.revoked = revoked;
        this.expiresAt = expiresAt;
    }

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }
}