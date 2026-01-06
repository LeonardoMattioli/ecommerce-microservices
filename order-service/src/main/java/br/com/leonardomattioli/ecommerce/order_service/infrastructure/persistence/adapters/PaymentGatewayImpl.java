package br.com.leonardomattioli.ecommerce.order_service.infrastructure.persistence.adapters;

import br.com.leonardomattioli.ecommerce.order_service.application.ports.outbound.PaymentGateway;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.external.PaymentClient;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.external.dtos.PaymentRequestDTO;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.external.dtos.PaymentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentGatewayImpl implements PaymentGateway {

    private final PaymentClient paymentClient;

    @Override
    public boolean requestPayment(UUID orderId, UUID userId, BigDecimal amount) {
        try {
            PaymentRequestDTO request = new PaymentRequestDTO(orderId, userId, amount);
            PaymentResponseDTO response = paymentClient.processPayment(request);
            
            return "APPROVED".equalsIgnoreCase(response.status());
        } catch (Exception e) {
            return false;
        }
    }
}