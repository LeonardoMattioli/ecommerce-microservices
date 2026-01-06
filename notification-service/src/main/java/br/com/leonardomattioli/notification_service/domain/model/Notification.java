package br.com.leonardomattioli.notification_service.domain.model;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public class Notification {
    private UUID id;
    private UUID orderId;
    private String email;
    private String message;
    private LocalDateTime sentAt;

    public Notification() {
    }

    public Notification(UUID id, UUID orderId, String email, String message, LocalDateTime sentAt) {
        this.id = id;
        this.orderId = orderId;
        this.email = email;
        this.message = message;
        this.sentAt = sentAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}