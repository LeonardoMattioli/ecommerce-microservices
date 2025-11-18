package br.com.leonardomattioli.ecommerce.catalog_service.infrastructure.persistence.adapters;

import br.com.leonardomattioli.ecommerce.catalog_service.application.port.outbound.ProductRepositoryPort;
import br.com.leonardomattioli.ecommerce.catalog_service.domain.models.Product;
import br.com.leonardomattioli.ecommerce.catalog_service.infrastructure.persistence.entity.CategoryEntity;
import br.com.leonardomattioli.ecommerce.catalog_service.infrastructure.persistence.entity.ProductEntity;
import br.com.leonardomattioli.ecommerce.catalog_service.infrastructure.persistence.mapper.CategoryMapper;
import br.com.leonardomattioli.ecommerce.catalog_service.infrastructure.persistence.mapper.ProductMapper;
import br.com.leonardomattioli.ecommerce.catalog_service.infrastructure.persistence.repository.CategoryJpaRepository;
import br.com.leonardomattioli.ecommerce.catalog_service.infrastructure.persistence.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final ProductJpaRepository jpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;

    @Override
    public Product save(Product product) {
        CategoryEntity categoryEntity = categoryJpaRepository
                .findById(product.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category entity not found"));

        ProductEntity productEntity = productMapper.toEntity(product, categoryEntity);
        
        ProductEntity savedEntity = jpaRepository.save(productEntity);
        
        return productMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return jpaRepository.findById(id).map(productMapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return jpaRepository.findAll().stream()
                .map(productMapper::toDomain)
                .toList();
    }
    
    @Override
    public Optional<Product> findBySku(String sku) {
        return jpaRepository.findBySku(sku).map(productMapper::toDomain);
    }
}