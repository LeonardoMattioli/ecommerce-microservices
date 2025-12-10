package br.com.leonardomattioli.invetory_service.application.dtos;

import java.util.UUID;

public record ReserveStockResponse(
    UUID reservationId
) {}