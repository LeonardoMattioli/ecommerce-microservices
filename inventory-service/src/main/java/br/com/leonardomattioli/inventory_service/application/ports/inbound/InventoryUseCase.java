package br.com.leonardomattioli.inventory_service.application.ports.inbound;

import java.util.UUID;

public interface InventoryUseCase {
    UUID reserveStock(UUID orderId, UUID productId, Integer quantity);
    void confirmReservation(UUID reservationId);
    void rollbackReservation(UUID reservationId);
}