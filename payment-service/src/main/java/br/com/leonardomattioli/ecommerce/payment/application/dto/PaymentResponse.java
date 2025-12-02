package br.com.leonardomattioli.ecommerce.payment.application.dto;

import br.com.leonardomattioli.ecommerce.payment.domain.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponse(
    UUID paymentId,
    UUID orderId,
    BigDecimal amount,
    PaymentStatus status,
    LocalDateTime processedAt
) {}