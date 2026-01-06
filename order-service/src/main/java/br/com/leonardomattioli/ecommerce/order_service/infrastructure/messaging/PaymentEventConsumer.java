package br.com.leonardomattioli.ecommerce.order_service.infrastructure.messaging;

import br.com.leonardomattioli.ecommerce.order_service.domain.enums.OrderStatus;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.persistence.entity.OrderEntity;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.persistence.repository.OrderJpaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventConsumer {

    private final OrderJpaRepository orderRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "payment.approved", groupId = "orders-group")
    @Transactional
    public void consumePaymentApproved(String payload) {
        log.info("Pagamento APROVADO recebido: {}", payload);
        updateOrderStatus(payload, OrderStatus.PAID);
    }

    @KafkaListener(topics = "payment.declined", groupId = "orders-group")
    @Transactional
    public void consumePaymentDeclined(String payload) {
        log.info("Pagamento RECUSADO recebido: {}", payload);
        updateOrderStatus(payload, OrderStatus.CANCELLED);
    }

    private void updateOrderStatus(String payload, OrderStatus newStatus) {
        try {
            JsonNode node = objectMapper.readTree(payload);
            UUID orderId = UUID.fromString(node.get("orderId").asText());

            OrderEntity order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + orderId));

            order.setStatus(newStatus);
            orderRepository.save(order);
            
            log.info("Pedido {} atualizado para {}", orderId, newStatus);

        } catch (Exception e) {
            log.error("Erro ao atualizar status do pedido: {}", e.getMessage());
        }
    }
}