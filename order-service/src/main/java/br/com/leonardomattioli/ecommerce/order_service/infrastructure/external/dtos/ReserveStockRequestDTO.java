package br.com.leonardomattioli.ecommerce.order_service.infrastructure.external.dtos;

import java.util.UUID;
public record ReserveStockRequestDTO(UUID orderId, UUID productId, Integer quantity) {}