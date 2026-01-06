package br.com.leonardomattioli.inventory_service.infrastructure.persistence.repository;

import br.com.leonardomattioli.inventory_service.infrastructure.persistence.entity.StockEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StockJpaRepository extends JpaRepository<StockEntity, UUID> {
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<StockEntity> findByProductId(UUID productId);
}