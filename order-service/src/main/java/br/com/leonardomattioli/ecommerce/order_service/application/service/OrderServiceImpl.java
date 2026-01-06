package br.com.leonardomattioli.ecommerce.order_service.application.service;

import br.com.leonardomattioli.ecommerce.order_service.application.dto.OrderCreateRequest;
import br.com.leonardomattioli.ecommerce.order_service.application.dto.OrderItemRequest;
import br.com.leonardomattioli.ecommerce.order_service.application.dto.OrderResponse;
import br.com.leonardomattioli.ecommerce.order_service.application.ports.inbound.CreateOrderUseCase;
import br.com.leonardomattioli.ecommerce.order_service.application.ports.outbound.CatalogGateway;
import br.com.leonardomattioli.ecommerce.order_service.application.ports.outbound.InventoryGateway;
import br.com.leonardomattioli.ecommerce.order_service.application.ports.outbound.OrderRepositoryPort;
import br.com.leonardomattioli.ecommerce.order_service.application.ports.outbound.PaymentGateway;
import br.com.leonardomattioli.ecommerce.order_service.domain.enums.OrderStatus;
import br.com.leonardomattioli.ecommerce.order_service.domain.model.Order;
import br.com.leonardomattioli.ecommerce.order_service.domain.model.OrderItem;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.persistence.entity.OutboxEntity;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.persistence.repository.OutboxJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements CreateOrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;
    private final CatalogGateway catalogGateway;
    private final PaymentGateway paymentGateway;
    private final InventoryGateway inventoryGateway;

    private final OutboxJpaRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request) {

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest itemRequest : request.items()) {
            BigDecimal realPrice = catalogGateway.getProductPrice(itemRequest.productId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + itemRequest.productId()));

            orderItems.add(OrderItem.builder()
                    .productId(itemRequest.productId())
                    .quantity(itemRequest.quantity())
                    .price(realPrice)
                    .build());
        }

        Order order = Order.builder()
                .userId(request.userId())
                .items(orderItems)
                .status(OrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();
        order.calculateTotal();

        Order savedOrder = orderRepositoryPort.save(order);

        try {
            String payload = objectMapper.writeValueAsString(savedOrder);

            OutboxEntity event = OutboxEntity.builder()
                    .id(UUID.randomUUID())
                    .aggregateType("ORDER")
                    .aggregateId(savedOrder.getId())
                    .type("ORDER_CREATED")
                    .payload(payload)
                    .status("NEW")
                    .createdAt(LocalDateTime.now())
                    .build();

            outboxRepository.save(event);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao persistir evento outbox", e);
        }

        try {
            UUID reservationId = null;
            for (OrderItem item : savedOrder.getItems()) {
                reservationId = inventoryGateway.reserveStock(
                        savedOrder.getId(),
                        item.getProductId(),
                        item.getQuantity()
                );
            }

            savedOrder.setReservationId(reservationId);
            orderRepositoryPort.save(savedOrder);

            boolean isPaid = paymentGateway.requestPayment(
                    savedOrder.getId(),
                    savedOrder.getUserId(),
                    savedOrder.getTotalAmount()
            );

            if (isPaid) {
                savedOrder.setStatus(OrderStatus.PAID);
                inventoryGateway.confirmReservation(reservationId);
            } else {
                savedOrder.setStatus(OrderStatus.CANCELLED);
                inventoryGateway.rollbackReservation(reservationId);
            }

        } catch (Exception e) {
            savedOrder.setStatus(OrderStatus.CANCELLED);
        }

        Order finalOrder = orderRepositoryPort.save(savedOrder);

        return new OrderResponse(
                finalOrder.getId(),
                finalOrder.getStatus().name(),
                finalOrder.getTotalAmount()
        );
    }
}
