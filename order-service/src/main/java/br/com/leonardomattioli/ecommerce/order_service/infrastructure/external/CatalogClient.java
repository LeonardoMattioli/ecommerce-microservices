package br.com.leonardomattioli.ecommerce.order_service.infrastructure.external;

import br.com.leonardomattioli.ecommerce.order_service.infrastructure.external.dtos.ProductResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "CATALOG-SERVICE")
public interface CatalogClient {
    @GetMapping("/api/products/{id}")
    ProductResponseDTO getProductById(@PathVariable("id") UUID id);
}