package br.com.leonardomattioli.ecommerce.order_service.infrastructure.persistence.repository;

import br.com.leonardomattioli.ecommerce.order_service.infrastructure.persistence.entity.OutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface OutboxJpaRepository extends JpaRepository<OutboxEntity, UUID> {
    List<OutboxEntity> findByStatus(String status);
}