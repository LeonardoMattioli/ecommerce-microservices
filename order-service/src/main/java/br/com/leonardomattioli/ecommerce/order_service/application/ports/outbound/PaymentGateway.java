package br.com.leonardomattioli.ecommerce.order_service.application.ports.outbound;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentGateway {
    boolean requestPayment(UUID orderId, UUID userId, BigDecimal amount);
}