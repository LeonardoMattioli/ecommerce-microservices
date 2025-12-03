package br.com.leonardomattioli.ecommerce.order_service.application.dto;

import java.util.List;
import java.util.UUID;

public record OrderCreateRequest(
    UUID userId,
    List<OrderItemRequest> items
) {}