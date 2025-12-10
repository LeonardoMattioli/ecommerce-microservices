package br.com.leonardomattioli.invetory_service.application.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReserveStockRequest(
    @NotNull UUID orderId,
    @NotNull UUID productId,
    @NotNull @Min(1) Integer quantity
) {}