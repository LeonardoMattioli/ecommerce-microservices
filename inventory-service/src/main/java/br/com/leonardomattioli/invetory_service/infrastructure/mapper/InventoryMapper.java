package br.com.leonardomattioli.invetory_service.infrastructure.mapper;

import br.com.leonardomattioli.invetory_service.domain.model.Reservation;
import br.com.leonardomattioli.invetory_service.domain.model.Stock;
import br.com.leonardomattioli.invetory_service.infrastructure.persistence.entity.ReservationEntity;
import br.com.leonardomattioli.invetory_service.infrastructure.persistence.entity.StockEntity;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {

    public Stock toStockDomain(StockEntity entity) {
        if (entity == null) return null;
        return Stock.builder()
                .productId(entity.getProductId())
                .quantity(entity.getQuantity())
                .reservedQuantity(entity.getReservedQuantity())
                .build();
    }

    public StockEntity toStockEntity(Stock domain) {
        if (domain == null) return null;
        return StockEntity.builder()
                .productId(domain.getProductId())
                .quantity(domain.getQuantity())
                .reservedQuantity(domain.getReservedQuantity())
                .build();
    }

    public Reservation toReservationDomain(ReservationEntity entity) {
        if (entity == null) return null;
        return Reservation.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .productId(entity.getProductId())
                .quantity(entity.getQuantity())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public ReservationEntity toReservationEntity(Reservation domain) {
        if (domain == null) return null;
        return ReservationEntity.builder()
                .id(domain.getId())
                .orderId(domain.getOrderId())
                .productId(domain.getProductId())
                .quantity(domain.getQuantity())
                .status(domain.getStatus())
                .createdAt(domain.getCreatedAt())
                .build();
    }
}