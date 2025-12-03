package br.com.leonardomattioli.ecommerce.order_service.application.ports.outbound;

import br.com.leonardomattioli.ecommerce.order_service.domain.model.Order;

public interface OrderRepositoryPort {
    Order save(Order order);
}