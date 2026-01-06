package br.com.leonardomattioli.ecommerce.catalog_service.infrastructure.persistence.adapters;

import br.com.leonardomattioli.ecommerce.catalog_service.application.port.outbound.CategoryRepositoryPort;
import br.com.leonardomattioli.ecommerce.catalog_service.domain.models.Category;
import br.com.leonardomattioli.ecommerce.catalog_service.infrastructure.persistence.entity.CategoryEntity;
import br.com.leonardomattioli.ecommerce.catalog_service.infrastructure.mapper.CategoryMapper;
import br.com.leonardomattioli.ecommerce.catalog_service.infrastructure.persistence.repository.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryRepositoryPort {

    private final CategoryJpaRepository jpaRepository;
    
    private final CategoryMapper categoryMapper;

    @Override
    public Category save(Category category) {
        CategoryEntity categoryEntity = categoryMapper.toEntity(category);
        CategoryEntity savedEntity = jpaRepository.save(categoryEntity);
        return categoryMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Category> findById(UUID id) {
        Optional<CategoryEntity> entityOptional = jpaRepository.findById(id);
        return entityOptional.map(categoryMapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
        List<CategoryEntity> entities = jpaRepository.findAll();
        return entities.stream()
                .map(categoryMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<Category> findByName(String name) {
        Optional<CategoryEntity> entityOptional = jpaRepository.findByName(name);
        return entityOptional.map(categoryMapper::toDomain);
    }
}