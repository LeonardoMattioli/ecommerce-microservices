package br.com.leonardomattioli.ecommerce.catalog_service.application.port.outbound;

import br.com.leonardomattioli.ecommerce.catalog_service.domain.models.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepositoryPort {
    Product save(Product product);
    Optional<Product> findById(UUID id);
    List<Product> findAll();
    Optional<Product> findBySku(String sku);
}