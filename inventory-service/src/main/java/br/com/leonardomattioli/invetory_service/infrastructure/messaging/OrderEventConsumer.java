package br.com.leonardomattioli.invetory_service.infrastructure.messaging;

import br.com.leonardomattioli.invetory_service.application.ports.inbound.InventoryUseCase;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

    private final InventoryUseCase inventoryUseCase;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order.created", groupId = "inventory-group")
    public void consumeOrderCreatedEvent(String payload) {
        log.info("Evento recebido no Inventory: {}", payload);

        try {
            JsonNode orderNode = objectMapper.readTree(payload);
            String orderId = orderNode.get("id").asText();
            String userId = orderNode.get("userId").asText();
            double totalAmount = orderNode.get("totalAmount").asDouble();

            JsonNode firstItem = orderNode.get("items").get(0);
            String productId = firstItem.get("productId").asText();
            int quantity = firstItem.get("quantity").asInt();

            inventoryUseCase.reserveStock(
                    UUID.fromString(orderId),
                    UUID.fromString(productId),
                    quantity
            );

            String eventPayload = String.format("{\"orderId\": \"%s\", \"userId\": \"%s\", \"amount\": %s}",
                    orderId, userId, totalAmount);
            
            kafkaTemplate.send("stock.reserved", orderId, eventPayload);
            log.info("Estoque reservado. Evento stock.reserved enviado.");

        } catch (Exception e) {
            log.error("Falha ao reservar estoque: {}", e.getMessage());
        }
    }
}