package br.com.leonardomattioli.inventory_service.domain.model;

import lombok.Builder;

import java.util.UUID;

@Builder
public class Stock {
    private UUID productId;
    private Integer quantity;
    private Integer reservedQuantity;

    public int getAvailableQuantity() {
        return quantity - reservedQuantity;
    }

    public void reserve(int amount) {
        if (getAvailableQuantity() < amount) {
            throw new RuntimeException("Insufficient stock for product: " + productId);
        }
        this.reservedQuantity += amount;
    }

    public void confirm(int amount) {
        this.reservedQuantity -= amount;
        this.quantity -= amount;
    }

    public void rollback(int amount) {
        this.reservedQuantity -= amount;
    }

    public Stock() {}

    public Stock(UUID productId, Integer quantity, Integer reservedQuantity) {
        this.productId = productId;
        this.quantity = quantity;
        this.reservedQuantity = reservedQuantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(Integer reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }
}