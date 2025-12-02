package br.com.leonardomattioli.ecommerce.payment.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequest(
    UUID orderId,
    UUID userId,
    BigDecimal amount
) {}