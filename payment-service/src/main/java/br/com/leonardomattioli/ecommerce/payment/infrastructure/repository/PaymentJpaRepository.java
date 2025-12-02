package br.com.leonardomattioli.ecommerce.payment.infrastructure.repository;

import br.com.leonardomattioli.ecommerce.payment.infrastructure.persistence.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, UUID> {
}