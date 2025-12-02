package br.com.leonardomattioli.ecommerce.payment.infrastructure.controller;

import br.com.leonardomattioli.ecommerce.payment.application.dto.PaymentRequest;
import br.com.leonardomattioli.ecommerce.payment.application.dto.PaymentResponse;
import br.com.leonardomattioli.ecommerce.payment.application.ports.inbound.ProcessPaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final ProcessPaymentUseCase processPaymentUseCase;

    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest request) {
        PaymentResponse response = processPaymentUseCase.processPayment(request);
        return ResponseEntity.ok(response);
    }
}