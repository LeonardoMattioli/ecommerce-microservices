package br.com.leonardomattioli.ecommerce.order_service.infrastructure.mapper;

import br.com.leonardomattioli.ecommerce.order_service.domain.model.Order;
import br.com.leonardomattioli.ecommerce.order_service.domain.model.OrderItem;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.persistence.entity.OrderEntity;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.persistence.entity.OrderItemEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderEntity toEntity(Order domain) {
        OrderEntity entity = OrderEntity.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .totalAmount(domain.getTotalAmount())
                .status(domain.getStatus())
                .createdAt(domain.getCreatedAt())
                .build();

        if (domain.getItems() != null) {
            List<OrderItemEntity> itemEntities = domain.getItems().stream()
                    .map(item -> OrderItemEntity.builder()
                            .productId(item.getProductId())
                            .quantity(item.getQuantity())
                            .price(item.getPrice())
                            .order(entity)
                            .build())
                    .collect(Collectors.toList());
            entity.setItems(itemEntities);
        }
        return entity;
    }

    public Order toDomain(OrderEntity entity) {
        List<OrderItem> items = entity.getItems().stream()
                .map(item -> OrderItem.builder()
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build())
                .collect(Collectors.toList());

        return Order.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .status(entity.getStatus())
                .totalAmount(entity.getTotalAmount())
                .createdAt(entity.getCreatedAt())
                .items(items)
                .build();
    }
}