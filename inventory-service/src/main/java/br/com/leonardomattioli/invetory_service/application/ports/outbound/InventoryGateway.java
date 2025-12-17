package br.com.leonardomattioli.invetory_service.application.ports.outbound;

import java.util.UUID;

public interface InventoryGateway {
    UUID reserveStock(UUID orderId, UUID productId, Integer quantity);
    void confirmReservation(UUID reservationId);
    void rollbackReservation(UUID reservationId);
}