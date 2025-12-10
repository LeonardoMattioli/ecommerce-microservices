package br.com.leonardomattioli.invetory_service.application.services;


import br.com.leonardomattioli.invetory_service.application.ports.inbound.InventoryUseCase;
import br.com.leonardomattioli.invetory_service.application.ports.outbound.InventoryRepositoryPort;
import br.com.leonardomattioli.invetory_service.domain.model.Reservation;
import br.com.leonardomattioli.invetory_service.domain.model.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryUseCase {

    private final InventoryRepositoryPort repository;

    @Override
    @Transactional
    public UUID reserveStock(UUID orderId, UUID productId, Integer quantity) {
        Stock stock = repository.findStockByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Stock not found for product"));

        stock.reserve(quantity);
        repository.saveStock(stock);

        Reservation reservation = Reservation.builder()
                .id(UUID.randomUUID())
                .orderId(orderId)
                .productId(productId)
                .quantity(quantity)
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();
        
        repository.saveReservation(reservation);
        
        return reservation.getId();
    }

    @Override
    @Transactional
    public void confirmReservation(UUID reservationId) {
        Reservation reservation = repository.findReservationById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (!"PENDING".equals(reservation.getStatus())) return;

        Stock stock = repository.findStockByProductId(reservation.getProductId()).get();
        stock.confirm(reservation.getQuantity());

        repository.saveStock(stock);
        
        reservation.setStatus("CONFIRMED");
        repository.saveReservation(reservation);
    }

    @Override
    @Transactional
    public void rollbackReservation(UUID reservationId) {
        Reservation reservation = repository.findReservationById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (!"PENDING".equals(reservation.getStatus())) return;

        Stock stock = repository.findStockByProductId(reservation.getProductId()).get();
        stock.rollback(reservation.getQuantity());

        repository.saveStock(stock);

        reservation.setStatus("CANCELLED");
        repository.saveReservation(reservation);
    }
}