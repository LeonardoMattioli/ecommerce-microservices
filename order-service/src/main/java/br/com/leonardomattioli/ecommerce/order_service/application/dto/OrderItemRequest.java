package br.com.leonardomattioli.ecommerce.order_service.application.dto;

import java.util.UUID;

public record OrderItemRequest(
    UUID productId,
    Integer quantity
) {}