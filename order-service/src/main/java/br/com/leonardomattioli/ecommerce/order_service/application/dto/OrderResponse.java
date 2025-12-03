package br.com.leonardomattioli.ecommerce.order_service.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderResponse(
    UUID orderId,
    String status,
    BigDecimal totalAmount
) {}