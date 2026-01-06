package br.com.leonardomattioli.notification_service.infrastructure.messaging;

import br.com.leonardomattioli.notification_service.application.ports.inbound.SendNotificationUseCase;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final SendNotificationUseCase sendNotificationUseCase;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "payment.approved", groupId = "notification-group")
    public void handleOrderPaid(String payload) {
        processEvent(payload, "Pedido Confirmado! Pagamento aprovado.");
    }

    @KafkaListener(topics = "payment.declined", groupId = "notification-group")
    public void handleOrderCancelled(String payload) {
        processEvent(payload, "Pedido Cancelado. Pagamento recusado.");
    }

    private void processEvent(String payload, String messageTemplate) {
        try {
            JsonNode node = objectMapper.readTree(payload);
            String orderIdStr = node.get("orderId").asText();
            String userIdStr = node.get("userId").asText();

            sendNotificationUseCase.sendOrderNotification(
                    UUID.fromString(orderIdStr),
                    userIdStr,
                    messageTemplate
            );

        } catch (Exception e) {
            log.error("Erro ao processar mensagem Kafka no Notification Service: {}", e.getMessage());
        }
    }
}