package br.com.leonardomattioli.inventory_service.application.dtos;

import java.util.UUID;

public record ReserveStockResponse(
    UUID reservationId
) {}