package br.com.leonardomattioli.notification_service.application.ports.outbound;

import br.com.leonardomattioli.notification_service.domain.model.Notification;

public interface NotificationRepositoryPort {
    Notification save(Notification notification);
}