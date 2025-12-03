package br.com.leonardomattioli.ecommerce.order_service.infrastructure.persistence.repository;

import br.com.leonardomattioli.ecommerce.order_service.infrastructure.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {}