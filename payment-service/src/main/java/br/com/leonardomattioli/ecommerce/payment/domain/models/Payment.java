package br.com.leonardomattioli.ecommerce.payment.domain.models;

import br.com.leonardomattioli.ecommerce.payment.domain.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public class Payment {
    private UUID id;
    private UUID orderId;
    private UUID userId;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDateTime createdAt;

    public Payment(){}

    public Payment(UUID id, UUID orderId, UUID userId, BigDecimal amount, PaymentStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}