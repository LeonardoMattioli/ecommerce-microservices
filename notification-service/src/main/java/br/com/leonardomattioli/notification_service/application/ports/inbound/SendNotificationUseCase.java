package br.com.leonardomattioli.notification_service.application.ports.inbound;

import java.util.UUID;

public interface SendNotificationUseCase {
    void sendOrderNotification(UUID orderId, String userId, String message);
}