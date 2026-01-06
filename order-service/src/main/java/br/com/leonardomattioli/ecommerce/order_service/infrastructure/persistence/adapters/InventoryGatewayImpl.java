package br.com.leonardomattioli.ecommerce.order_service.infrastructure.persistence.adapters;

import br.com.leonardomattioli.ecommerce.order_service.application.ports.outbound.InventoryGateway;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.external.InventoryClient;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.external.dtos.ReserveStockRequestDTO;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.external.dtos.ReserveStockResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InventoryGatewayImpl implements InventoryGateway {

    private final InventoryClient inventoryClient;

    @Override
    public UUID reserveStock(UUID orderId, UUID productId, Integer quantity) {
        try {
            ReserveStockRequestDTO request = new ReserveStockRequestDTO(orderId, productId, quantity);
            ReserveStockResponseDTO response = inventoryClient.reserveStock(request);
            return response.reservationId();
        } catch (Exception e) {
            throw new RuntimeException("Failed to reserve stock. Service unavailable or insufficient stock.", e);
        }
    }

    @Override
    public void confirmReservation(UUID reservationId) {
        try {
            inventoryClient.confirmReservation(reservationId);
        } catch (Exception e) {
            System.err.println("Error confirming reservation: " + e.getMessage());
        }
    }

    @Override
    public void rollbackReservation(UUID reservationId) {
        try {
            inventoryClient.rollbackReservation(reservationId);
        } catch (Exception e) {
            System.err.println("Error rolling back reservation: " + e.getMessage());
        }
    }
}