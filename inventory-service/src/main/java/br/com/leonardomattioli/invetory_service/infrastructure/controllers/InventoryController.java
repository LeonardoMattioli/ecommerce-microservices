package br.com.leonardomattioli.invetory_service.infrastructure.controllers;

import br.com.leonardomattioli.invetory_service.application.dtos.ReserveStockRequest;
import br.com.leonardomattioli.invetory_service.application.dtos.ReserveStockResponse;
import br.com.leonardomattioli.invetory_service.application.ports.inbound.InventoryUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryUseCase inventoryUseCase;

    @PostMapping("/reserve")
    public ResponseEntity<ReserveStockResponse> reserveStock(@Valid @RequestBody ReserveStockRequest request) {
        UUID reservationId = inventoryUseCase.reserveStock(
                request.orderId(),
                request.productId(),
                request.quantity()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ReserveStockResponse(reservationId));
    }

    @PostMapping("/confirm/{reservationId}")
    public ResponseEntity<Void> confirmReservation(@PathVariable UUID reservationId) {
        inventoryUseCase.confirmReservation(reservationId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/rollback/{reservationId}")
    public ResponseEntity<Void> rollbackReservation(@PathVariable UUID reservationId) {
        inventoryUseCase.rollbackReservation(reservationId);
        return ResponseEntity.noContent().build();
    }
}