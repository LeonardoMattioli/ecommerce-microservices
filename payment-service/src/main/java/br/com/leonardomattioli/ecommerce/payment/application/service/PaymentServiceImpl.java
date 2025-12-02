package br.com.leonardomattioli.ecommerce.payment.application.service;

import br.com.leonardomattioli.ecommerce.payment.application.dto.PaymentRequest;
import br.com.leonardomattioli.ecommerce.payment.application.dto.PaymentResponse;
import br.com.leonardomattioli.ecommerce.payment.application.ports.inbound.ProcessPaymentUseCase;
import br.com.leonardomattioli.ecommerce.payment.application.ports.outbound.PaymentRepositoryPort;
import br.com.leonardomattioli.ecommerce.payment.domain.enums.PaymentStatus;
import br.com.leonardomattioli.ecommerce.payment.domain.models.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements ProcessPaymentUseCase {

    private final PaymentRepositoryPort paymentRepositoryPort;

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        log.info("Recebendo pagamento para o pedido: {}", request.orderId());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        PaymentStatus finalStatus;
        if (request.amount().doubleValue() > 10000) {
            finalStatus = PaymentStatus.DECLINED;
        } else {
            finalStatus = PaymentStatus.APPROVED;
        }

        Payment payment = Payment.builder()
                .id(UUID.randomUUID())
                .orderId(request.orderId())
                .userId(request.userId())
                .amount(request.amount())
                .status(finalStatus)
                .createdAt(LocalDateTime.now())
                .build();

        Payment savedPayment = paymentRepositoryPort.save(payment);

        log.info("Pagamento processado com status: {}", savedPayment.getStatus());

        return new PaymentResponse(
                savedPayment.getId(),
                savedPayment.getOrderId(),
                savedPayment.getAmount(),
                savedPayment.getStatus(),
                savedPayment.getCreatedAt()
        );
    }
}