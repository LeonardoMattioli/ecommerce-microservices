package br.com.leonardomattioli.notification_service.infrastructure.persistence.repository;

import br.com.leonardomattioli.notification_service.infrastructure.persistence.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, UUID> {
}