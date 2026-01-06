package br.com.leonardomattioli.notification_service.infrastructure.adapters;

import br.com.leonardomattioli.notification_service.application.ports.outbound.NotificationRepositoryPort;
import br.com.leonardomattioli.notification_service.domain.model.Notification;
import br.com.leonardomattioli.notification_service.infrastructure.persistence.entity.NotificationEntity;
import br.com.leonardomattioli.notification_service.infrastructure.mapper.NotificationMapper;
import br.com.leonardomattioli.notification_service.infrastructure.persistence.repository.NotificationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationRepositoryAdapter implements NotificationRepositoryPort {

    private final NotificationJpaRepository jpaRepository;
    private final NotificationMapper mapper;

    @Override
    public Notification save(Notification notification) {
        NotificationEntity entity = mapper.toEntity(notification);
        NotificationEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
}