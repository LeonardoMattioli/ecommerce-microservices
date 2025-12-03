package br.com.leonardomattioli.ecommerce.order_service.infrastructure.external.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequestDTO(UUID orderId, UUID userId, BigDecimal amount) {}