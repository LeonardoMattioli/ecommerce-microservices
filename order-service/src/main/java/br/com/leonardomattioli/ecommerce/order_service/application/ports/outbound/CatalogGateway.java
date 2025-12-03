package br.com.leonardomattioli.ecommerce.order_service.application.ports.outbound;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface CatalogGateway {
    Optional<BigDecimal> getProductPrice(UUID productId);
}