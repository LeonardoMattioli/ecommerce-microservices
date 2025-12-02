package br.com.leonardomattioli.ecommerce.payment.infrastructure.adapters;

import br.com.leonardomattioli.ecommerce.payment.application.ports.outbound.PaymentRepositoryPort;
import br.com.leonardomattioli.ecommerce.payment.domain.models.Payment;
import br.com.leonardomattioli.ecommerce.payment.infrastructure.mapper.PaymentMapper;
import br.com.leonardomattioli.ecommerce.payment.infrastructure.persistence.entity.PaymentEntity;
import br.com.leonardomattioli.ecommerce.payment.infrastructure.repository.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryAdapter implements PaymentRepositoryPort {

    private final PaymentJpaRepository jpaRepository;
    private final PaymentMapper mapper;

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = mapper.toEntity(payment);
        PaymentEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
}