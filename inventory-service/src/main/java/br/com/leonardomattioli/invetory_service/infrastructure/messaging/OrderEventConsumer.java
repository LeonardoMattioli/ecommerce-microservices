package br.com.leonardomattioli.invetory_service.infrastructure.messaging;

import br.com.leonardomattioli.invetory_service.application.ports.inbound.InventoryUseCase;
import br.com.leonardomattioli.invetory_service.infrastructure.persistence.entity.ProcessedEventEntity;
import br.com.leonardomattioli.invetory_service.infrastructure.persistence.repository.ProcessedEventRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

    private final InventoryUseCase inventoryUseCase;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final ProcessedEventRepository processedEventRepository;

    @KafkaListener(topics = "order.created", groupId = "inventory-group")
    @Retry(name = "inventoryConsumer", fallbackMethod = "consumeFallback")
    @Transactional
    public void consumeOrderCreatedEvent(String payload) {
        log.info("Evento recebido no Inventory: {}", payload);

        try {
            JsonNode orderNode = objectMapper.readTree(payload);
            String orderIdStr = orderNode.get("id").asText();
            UUID orderId = UUID.fromString(orderIdStr);

            if (processedEventRepository.existsById(orderId)) {
                log.warn("Evento duplicado ignorado: {}", orderId);
                return;
            }

            processedEventRepository.saveAndFlush(ProcessedEventEntity.builder()
                    .eventId(orderId)
                    .receivedAt(LocalDateTime.now())
                    .build());

            String userId = orderNode.get("userId").asText();
            double totalAmount = orderNode.get("totalAmount").asDouble();

            JsonNode firstItem = orderNode.get("items").get(0);
            String productId = firstItem.get("productId").asText();
            int quantity = firstItem.get("quantity").asInt();

            inventoryUseCase.reserveStock(
                    orderId,
                    UUID.fromString(productId),
                    quantity
            );

            String eventPayload = String.format("{\"orderId\": \"%s\", \"userId\": \"%s\", \"amount\": %s}",
                    orderIdStr, userId, totalAmount);

            kafkaTemplate.send("stock.reserved", orderIdStr, eventPayload);
            log.info("Estoque reservado com sucesso. Evento enviado.");

        } catch (DataIntegrityViolationException e) {
            log.warn("Evento duplicado detectado (Race Condition no banco): {}", e.getMessage());
        } catch (Exception e) {
            log.error("Erro no processamento: {}", e.getMessage());
            throw new RuntimeException("Forçando Retry devido a erro no processamento", e);
        }
    }

    public void consumeFallback(String payload, Throwable t) {
        log.error("Todas as tentativas de Retry falharam para o payload. Enviando para DLQ (Dead Letter Queue) ou logando erro fatal. Erro: {}", t.getMessage());
    }
}