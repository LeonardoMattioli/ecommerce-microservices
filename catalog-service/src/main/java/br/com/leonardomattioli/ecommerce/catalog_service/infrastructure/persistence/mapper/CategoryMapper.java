package br.com.leonardomattioli.ecommerce.catalog_service.infrastructure.persistence.mapper;

import br.com.leonardomattioli.ecommerce.catalog_service.domain.models.Category;
import br.com.leonardomattioli.ecommerce.catalog_service.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category toDomain(CategoryEntity entity) {
        if (entity == null) {
            return null;
        }

        return Category.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public CategoryEntity toEntity(Category domain) {
        if (domain == null) {
            return null;
        }

        CategoryEntity entity = new CategoryEntity();
        
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        
        return entity;
    }
}