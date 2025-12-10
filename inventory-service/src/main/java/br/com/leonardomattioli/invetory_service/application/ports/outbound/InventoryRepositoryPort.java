package br.com.leonardomattioli.invetory_service.application.ports.outbound;

import br.com.leonardomattioli.invetory_service.domain.model.Reservation;
import br.com.leonardomattioli.invetory_service.domain.model.Stock;
import java.util.Optional;
import java.util.UUID;

public interface InventoryRepositoryPort {
    Optional<Stock> findStockByProductId(UUID productId);
    void saveStock(Stock stock);
    
    void saveReservation(Reservation reservation);
    Optional<Reservation> findReservationById(UUID reservationId);
}