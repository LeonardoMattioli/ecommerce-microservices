package br.com.leonardomattioli.inventory_service.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "processed_events")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessedEventEntity {
    @Id
    @Column(name = "event_id")
    private UUID eventId;

    @Column(name = "received_at")
    private LocalDateTime receivedAt;
}