package br.com.leonardomattioli.inventory_service.infrastructure.persistence.repository;

import br.com.leonardomattioli.inventory_service.infrastructure.persistence.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, UUID> {
}