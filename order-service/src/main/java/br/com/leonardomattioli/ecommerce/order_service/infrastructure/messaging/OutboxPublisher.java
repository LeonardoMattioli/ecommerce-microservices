package br.com.leonardomattioli.ecommerce.order_service.infrastructure.messaging;

import br.com.leonardomattioli.ecommerce.order_service.infrastructure.persistence.entity.OutboxEntity;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.persistence.repository.OutboxJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxPublisher {

    private final OutboxJpaRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedRate = 5000)
    @Transactional
    public void publishNewEvents() {
        List<OutboxEntity> events = outboxRepository.findByStatus("NEW");

        if (!events.isEmpty()) {
            log.info("Encontrados {} eventos para publicar.", events.size());
        }

        for (OutboxEntity event : events) {
            try {
                kafkaTemplate.send("order.created", event.getAggregateId().toString(), event.getPayload())
                        .whenComplete((result, ex) -> {
                            if (ex == null) {
                                log.info("Evento {} enviado com sucesso para o Kafka.", event.getId());
                            } else {
                                log.error("Erro ao enviar evento Kafka: {}", ex.getMessage());
                            }
                        });

                event.setStatus("PUBLISHED");
                outboxRepository.save(event);

            } catch (Exception e) {
                log.error("Falha ao processar evento outbox {}", event.getId(), e);
            }
        }
    }
}