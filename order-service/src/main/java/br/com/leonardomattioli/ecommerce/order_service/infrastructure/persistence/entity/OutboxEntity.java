package br.com.leonardomattioli.ecommerce.order_service.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "saga_outbox")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OutboxEntity {

    @Id
    private UUID id;

    private String aggregateType;
    private UUID aggregateId;
    private String type;

    @Column(columnDefinition = "TEXT")
    private String payload;

    private String status;
    private LocalDateTime createdAt;
}