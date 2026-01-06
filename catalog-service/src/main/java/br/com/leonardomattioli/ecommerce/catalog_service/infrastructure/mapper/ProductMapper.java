package br.com.leonardomattioli.ecommerce.catalog_service.infrastructure.mapper;

import br.com.leonardomattioli.ecommerce.catalog_service.domain.models.Category;
import br.com.leonardomattioli.ecommerce.catalog_service.domain.models.Product;
import br.com.leonardomattioli.ecommerce.catalog_service.infrastructure.persistence.entity.CategoryEntity;
import br.com.leonardomattioli.ecommerce.catalog_service.infrastructure.persistence.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toDomain(ProductEntity entity) {
        Category categoryDomain = null;
        if (entity.getCategory() != null) {
            categoryDomain = Category.builder()
                    .id(entity.getCategory().getId())
                    .name(entity.getCategory().getName())
                    .description(entity.getCategory().getDescription())
                    .build();
        }

        return Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .sku(entity.getSku())
                .imageUrl(entity.getImageUrl())
                .category(categoryDomain)
                .build();
    }
    public ProductEntity toEntity(Product domain, CategoryEntity categoryEntity) {
        ProductEntity entity = new ProductEntity();

        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setPrice(domain.getPrice());
        entity.setSku(domain.getSku());
        entity.setImageUrl(domain.getImageUrl());

        entity.setCategory(categoryEntity);

        return entity;
    }
}