package br.com.leonardomattioli.ecommerce.payment.application.ports.outbound;

import br.com.leonardomattioli.ecommerce.payment.domain.models.Payment;

public interface PaymentRepositoryPort {
    Payment save(Payment payment);
}