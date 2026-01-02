package br.com.leonardomattioli.inventory_service.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "stocks")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StockEntity {
    
    @Id
    @Column(name = "product_id")
    private UUID productId;

    private Integer quantity;

    @Column(name = "reserved_quantity")
    private Integer reservedQuantity;
}