package br.com.leonardomattioli.ecommerce.order_service.infrastructure.external;

import br.com.leonardomattioli.ecommerce.order_service.infrastructure.external.dtos.PaymentRequestDTO;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.external.dtos.PaymentResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentClient {
    @PostMapping("/api/payments")
    PaymentResponseDTO processPayment(@RequestBody PaymentRequestDTO request);
}