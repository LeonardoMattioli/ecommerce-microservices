package br.com.leonardomattioli.ecommerce.order_service.infrastructure.adapters;

import br.com.leonardomattioli.ecommerce.order_service.application.ports.outbound.CatalogGateway;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.external.CatalogClient;
import br.com.leonardomattioli.ecommerce.order_service.infrastructure.external.dtos.ProductResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CatalogGatewayImpl implements CatalogGateway {

    private final CatalogClient catalogClient;

    @Override
    public Optional<BigDecimal> getProductPrice(UUID productId) {
        try {
            ProductResponseDTO response = catalogClient.getProductById(productId);
            return Optional.ofNullable(response.price());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}