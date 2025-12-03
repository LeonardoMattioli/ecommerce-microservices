package br.com.leonardomattioli.ecommerce.order_service.application.ports.inbound;

import br.com.leonardomattioli.ecommerce.order_service.application.dto.OrderCreateRequest;
import br.com.leonardomattioli.ecommerce.order_service.application.dto.OrderResponse;

public interface CreateOrderUseCase {
    OrderResponse createOrder(OrderCreateRequest request);
}