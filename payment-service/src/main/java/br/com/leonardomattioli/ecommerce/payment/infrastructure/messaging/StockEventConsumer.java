package br.com.leonardomattioli.ecommerce.payment.infrastructure.messaging;

import br.com.leonardomattioli.ecommerce.payment.application.dto.PaymentRequest;
import br.com.leonardomattioli.ecommerce.payment.application.dto.PaymentResponse;
import br.com.leonardomattioli.ecommerce.payment.application.ports.inbound.ProcessPaymentUseCase;
import br.com.leonardomattioli.ecommerce.payment.domain.enums.PaymentStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockEventConsumer {

    private final ProcessPaymentUseCase processPaymentUseCase;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "stock.reserved", groupId = "payment-group")
    public void consumeStockReservedEvent(String payload) {
        log.info("Evento recebido no Payment: {}", payload);

        try {
            JsonNode node = objectMapper.readTree(payload);
            UUID orderId = UUID.fromString(node.get("orderId").asText());
            UUID userId = UUID.fromString(node.get("userId").asText());
            BigDecimal amount = BigDecimal.valueOf(node.get("amount").asDouble());

            PaymentRequest request = new PaymentRequest(orderId, userId, amount);
            PaymentResponse response = processPaymentUseCase.processPayment(request);

            if (response.status() == PaymentStatus.APPROVED) {
                kafkaTemplate.send("payment.approved", orderId.toString(), payload);
                log.info("Pagamento Aprovado. Evento enviado.");
            } else {
                kafkaTemplate.send("payment.declined", orderId.toString(), payload);
                log.info("Pagamento Recusado. Evento enviado.");
            }

        } catch (Exception e) {
            log.error("Erro ao processar pagamento via evento: {}", e.getMessage());
        }
    }
}