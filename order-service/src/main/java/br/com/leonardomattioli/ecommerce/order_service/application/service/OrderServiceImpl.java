package br.com.leonardomattioli.ecommerce.order_service.application.service;

import br.com.leonardomattioli.ecommerce.order_service.application.dto.OrderCreateRequest;
import br.com.leonardomattioli.ecommerce.order_service.application.dto.OrderItemRequest;
import br.com.leonardomattioli.ecommerce.order_service.application.dto.OrderResponse;
import br.com.leonardomattioli.ecommerce.order_service.application.ports.inbound.CreateOrderUseCase;
import br.com.leonardomattioli.ecommerce.order_service.application.ports.outbound.CatalogGateway;
import br.com.leonardomattioli.ecommerce.order_service.application.ports.outbound.OrderRepositoryPort;
import br.com.leonardomattioli.ecommerce.order_service.application.ports.outbound.PaymentGateway;
import br.com.leonardomattioli.ecommerce.order_service.domain.enums.OrderStatus;
import br.com.leonardomattioli.ecommerce.order_service.domain.model.Order;
import br.com.leonardomattioli.ecommerce.order_service.domain.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements CreateOrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;
    private final CatalogGateway catalogGateway;
    private final PaymentGateway paymentGateway;

    @Override
    public OrderResponse createOrder(OrderCreateRequest request) {
        List<OrderItem> orderItens = new ArrayList<>();

        for (OrderItemRequest itemRequest : request.items()) {
            BigDecimal realPrice = catalogGateway.getProductPrice(itemRequest.productId()).
                    orElseThrow(
                            () -> new IllegalArgumentException(
                                    "Product not found or Unavailable" + itemRequest.productId()));

            OrderItem item = OrderItem.builder()
                    .productId(itemRequest.productId())
                    .quantity(itemRequest.quantity())
                    .price(realPrice)
                    .build();
            orderItens.add(item);
        }

        Order order = Order.builder()
                .userId(request.userId())
                .items(orderItens)
                .status(OrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        order.calculateTotal();

        Order savedOrder = orderRepositoryPort.save(order);
        boolean isPaid = paymentGateway.requestPayment(savedOrder.getId(), savedOrder.getUserId(), savedOrder.getTotalAmount());

        if (isPaid) {
            savedOrder.setStatus(OrderStatus.PAID);
        } else {
            savedOrder.setStatus(OrderStatus.CANCELLED);
        }

        Order finalOrder = orderRepositoryPort.save(savedOrder);

        return new OrderResponse(finalOrder.getId(), finalOrder.getStatus().name(), finalOrder.getTotalAmount());
    }
}
