package br.com.leonardomattioli.ecommerce.payment.infrastructure.mapper;

import br.com.leonardomattioli.ecommerce.payment.domain.models.Payment;
import br.com.leonardomattioli.ecommerce.payment.infrastructure.persistence.entity.PaymentEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment toDomain(PaymentEntity entity) {
        return Payment.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .userId(entity.getUserId())
                .amount(entity.getAmount())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public PaymentEntity toEntity(Payment domain) {
        return PaymentEntity.builder()
                .id(domain.getId())
                .orderId(domain.getOrderId())
                .userId(domain.getUserId())
                .amount(domain.getAmount())
                .status(domain.getStatus())
                .createdAt(domain.getCreatedAt())
                .build();
    }
}