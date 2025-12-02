package br.com.leonardomattioli.ecommerce.payment.application.ports.inbound;

import br.com.leonardomattioli.ecommerce.payment.application.dto.PaymentRequest;
import br.com.leonardomattioli.ecommerce.payment.application.dto.PaymentResponse;

public interface ProcessPaymentUseCase {
    PaymentResponse processPayment(PaymentRequest request);
}