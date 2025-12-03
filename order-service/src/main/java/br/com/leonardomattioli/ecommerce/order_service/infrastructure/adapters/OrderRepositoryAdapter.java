package br.com.leonardomattioli.ecommerce.order_service.infrastructure.adapters;

import br.com.leonardomattioli.ecommerce.order_service.application.ports.outbound.OrderRepositoryPort;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.mapper.OrderMapper;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.persistence.entity.OrderEntity;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.persistence.repository.OrderJpaRepository;
import br.com.leonardomattioli.ecommerce.order_service.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private final OrderJpaRepository jpaRepository;
    private final OrderMapper mapper;

    @Override
    public Order save(Order order) {
        OrderEntity entity = mapper.toEntity(order);
        OrderEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }
}