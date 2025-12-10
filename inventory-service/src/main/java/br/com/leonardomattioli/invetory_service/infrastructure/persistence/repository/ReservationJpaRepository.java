package br.com.leonardomattioli.invetory_service.infrastructure.persistence.repository;

import br.com.leonardomattioli.invetory_service.infrastructure.persistence.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, UUID> {
}