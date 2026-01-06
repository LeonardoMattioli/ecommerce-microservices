package br.com.leonardomattioli.ecommerce.order_service.infrastructure.persistence.adapters;

import br.com.leonardomattioli.ecommerce.order_service.application.ports.outbound.CatalogGateway;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.external.CatalogClient;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.external.dtos.ProductResponseDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class CatalogGatewayImpl implements CatalogGateway {

    private final CatalogClient catalogClient;

    @Override
    @CircuitBreaker(name = "catalog", fallbackMethod = "fallbackGetProductPrice")
    public Optional<BigDecimal> getProductPrice(UUID productId) {
        try {
            ProductResponseDTO response = catalogClient.getProductById(productId);
            return Optional.ofNullable(response.price());
        } catch (Exception e) {
            throw e;
        }
    }

    public Optional<BigDecimal> fallbackGetProductPrice(UUID productId, Throwable t) {
        log.error("Catalog Service indisponível ou erro ao buscar produto {}. Circuito aberto ou erro. Detalhe: {}", productId, t.getMessage());

        return Optional.empty();
    }
}