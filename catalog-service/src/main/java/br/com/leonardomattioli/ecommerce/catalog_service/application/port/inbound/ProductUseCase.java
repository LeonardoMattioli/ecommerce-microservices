package br.com.leonardomattioli.ecommerce.catalog_service.application.port.inbound;

import br.com.leonardomattioli.ecommerce.catalog_service.application.dto.ProductCreateRequest;
import br.com.leonardomattioli.ecommerce.catalog_service.application.dto.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductUseCase {
    ProductResponse createProduct(ProductCreateRequest request);
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(UUID id);
}