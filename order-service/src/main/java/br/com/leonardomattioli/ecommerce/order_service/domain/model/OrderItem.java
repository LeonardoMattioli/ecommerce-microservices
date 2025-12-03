package br.com.leonardomattioli.ecommerce.order_service.domain.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public class OrderItem {
    private UUID productId;
    private Integer quantity;
    private BigDecimal price;

    public BigDecimal getSubTotal() {
        if (price == null || quantity == null) {
            return BigDecimal.ZERO;
        }
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public OrderItem() {}

    public OrderItem(UUID productId, Integer quantity, BigDecimal price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}