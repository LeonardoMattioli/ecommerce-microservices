package br.com.leonardomattioli.ecommerce.order_service.infrastructure.external;

import br.com.leonardomattioli.ecommerce.order_service.infrastructure.external.dtos.ReserveStockRequestDTO;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.external.dtos.ReserveStockResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryClient {

    @PostMapping("/api/inventory/reserve")
    ReserveStockResponseDTO reserveStock(@RequestBody ReserveStockRequestDTO request);

    @PostMapping("/api/inventory/confirm/{reservationId}")
    void confirmReservation(@PathVariable("reservationId") UUID reservationId);

    @PostMapping("/api/inventory/rollback/{reservationId}")
    void rollbackReservation(@PathVariable("reservationId") UUID reservationId);
}