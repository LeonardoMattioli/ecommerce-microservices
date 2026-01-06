package br.com.leonardomattioli.notification_service.infrastructure.mapper;

import br.com.leonardomattioli.notification_service.domain.model.Notification;
import br.com.leonardomattioli.notification_service.infrastructure.persistence.entity.NotificationEntity;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationEntity toEntity(Notification domain) {
        if (domain == null) return null;
        return NotificationEntity.builder()
                .id(domain.getId())
                .orderId(domain.getOrderId())
                .email(domain.getEmail())
                .message(domain.getMessage())
                .sentAt(domain.getSentAt())
                .build();
    }

    public Notification toDomain(NotificationEntity entity) {
        if (entity == null) return null;
        return Notification.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .email(entity.getEmail())
                .message(entity.getMessage())
                .sentAt(entity.getSentAt())
                .build();
    }
}