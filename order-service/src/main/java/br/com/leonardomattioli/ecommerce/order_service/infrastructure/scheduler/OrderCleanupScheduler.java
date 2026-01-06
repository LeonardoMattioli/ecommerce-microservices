package br.com.leonardomattioli.ecommerce.order_service.infrastructure.scheduler;

import br.com.leonardomattioli.ecommerce.order_service.application.ports.outbound.InventoryGateway;
import br.com.leonardomattioli.ecommerce.order_service.domain.enums.OrderStatus;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.persistence.entity.OrderEntity;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.persistence.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCleanupScheduler {

    private final OrderJpaRepository orderJpaRepository;
    private final InventoryGateway inventoryGateway;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void cancelExpiredOrders() {
        LocalDateTime expirationTime = LocalDateTime.now().minusMinutes(15);

        List<OrderEntity> expiredOrders = orderJpaRepository.findByStatusAndCreatedAtBefore(
                OrderStatus.CREATED,
                expirationTime
        );

        if (!expiredOrders.isEmpty()) {
            log.info("Found {} expired orders. Cancelling...", expiredOrders.size());
        }

        for (OrderEntity order : expiredOrders) {
            if (order.getReservationId() != null) {
                try {
                    inventoryGateway.rollbackReservation(order.getReservationId());
                } catch (Exception e) {
                    log.error("Failed to rollback inventory for order {}", order.getId());
                }
            }

            order.setStatus(OrderStatus.CANCELLED);
            orderJpaRepository.save(order);
            
            log.info("Order {} cancelled due to timeout.", order.getId());
        }
    }
}