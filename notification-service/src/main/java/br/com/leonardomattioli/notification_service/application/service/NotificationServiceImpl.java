package br.com.leonardomattioli.notification_service.application.service;

import br.com.leonardomattioli.notification_service.application.ports.inbound.SendNotificationUseCase;
import br.com.leonardomattioli.notification_service.application.ports.outbound.NotificationRepositoryPort;
import br.com.leonardomattioli.notification_service.domain.model.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements SendNotificationUseCase {

    private final NotificationRepositoryPort repositoryPort;

    @Override
    public void sendOrderNotification(UUID orderId, String userId, String message) {
        String userEmail = "cliente-" + userId.substring(0, 8) + "@email.com";

        log.info("📧 PROCESSANDO ENVIO PARA [{}]: '{}' (Pedido: {})", userEmail, message, orderId);

        // Cria o objeto de domínio puro
        Notification notification = Notification.builder()
                .id(UUID.randomUUID())
                .orderId(orderId)
                .email(userEmail)
                .message(message)
                .sentAt(LocalDateTime.now())
                .build();

        repositoryPort.save(notification);
        
        log.info("✅ Notificação salva no histórico com ID: {}", notification.getId());
    }
}