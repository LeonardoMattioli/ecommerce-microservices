package br.com.leonardomattioli.ecommerce.catalog_service.application.service;

import br.com.leonardomattioli.ecommerce.catalog_service.application.dto.ProductCreateRequest;
import br.com.leonardomattioli.ecommerce.catalog_service.application.dto.ProductResponse;
import br.com.leonardomattioli.ecommerce.catalog_service.application.port.inbound.ProductUseCase;
import br.com.leonardomattioli.ecommerce.catalog_service.application.port.outbound.CategoryRepositoryPort;
import br.com.leonardomattioli.ecommerce.catalog_service.application.port.outbound.ProductRepositoryPort;
import br.com.leonardomattioli.ecommerce.catalog_service.domain.exception.CategoryNotFoundException;
import br.com.leonardomattioli.ecommerce.catalog_service.domain.exception.ProductNotFoundException;
import br.com.leonardomattioli.ecommerce.catalog_service.domain.models.Category;
import br.com.leonardomattioli.ecommerce.catalog_service.domain.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductUseCase {

    private final ProductRepositoryPort productRepositoryPort;
    private final CategoryRepositoryPort categoryRepositoryPort;

    @Override
    public ProductResponse createProduct(ProductCreateRequest request) {
        if (productRepositoryPort.findBySku(request.sku()).isPresent()) {
            throw new IllegalArgumentException("SKU already exists");
        }

        Category category = categoryRepositoryPort.findById(request.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        Product newProduct = Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .sku(request.sku())
                .imageUrl(request.imageUrl())
                .category(category)
                .build();

        Product savedProduct = productRepositoryPort.save(newProduct);

        return toResponse(savedProduct);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepositoryPort.findAll();

        return products.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(UUID id) {
        return productRepositoryPort.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getSku(),
                product.getImageUrl(),
                product.getCategory().getName()
        );
    }
}