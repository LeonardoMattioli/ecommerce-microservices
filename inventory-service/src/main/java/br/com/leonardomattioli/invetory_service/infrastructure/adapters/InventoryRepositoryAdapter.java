package br.com.leonardomattioli.invetory_service.infrastructure.adapters;

import br.com.leonardomattioli.invetory_service.application.ports.outbound.InventoryRepositoryPort;
import br.com.leonardomattioli.invetory_service.domain.model.Reservation;
import br.com.leonardomattioli.invetory_service.domain.model.Stock;
import br.com.leonardomattioli.invetory_service.infrastructure.mapper.InventoryMapper;
import br.com.leonardomattioli.invetory_service.infrastructure.persistence.entity.ReservationEntity;
import br.com.leonardomattioli.invetory_service.infrastructure.persistence.entity.StockEntity;
import br.com.leonardomattioli.invetory_service.infrastructure.persistence.repository.ReservationJpaRepository;
import br.com.leonardomattioli.invetory_service.infrastructure.persistence.repository.StockJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class InventoryRepositoryAdapter implements InventoryRepositoryPort {

    private final StockJpaRepository stockJpaRepository;
    private final ReservationJpaRepository reservationJpaRepository;
    private final InventoryMapper mapper;

    @Override
    public Optional<Stock> findStockByProductId(UUID productId) {
        return stockJpaRepository.findByProductId(productId)
                .map(mapper::toStockDomain);
    }

    @Override
    public void saveStock(Stock stock) {
        StockEntity entity = mapper.toStockEntity(stock);
        stockJpaRepository.save(entity);
    }

    @Override
    public void saveReservation(Reservation reservation) {
        ReservationEntity entity = mapper.toReservationEntity(reservation);
        reservationJpaRepository.save(entity);
    }

    @Override
    public Optional<Reservation> findReservationById(UUID reservationId) {
        return reservationJpaRepository.findById(reservationId)
                .map(mapper::toReservationDomain);
    }
}