package br.com.leonardomattioli.invetory_service.infrastructure.persistence.repository;

import br.com.leonardomattioli.invetory_service.infrastructure.persistence.entity.ProcessedEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEventEntity, UUID> {
}