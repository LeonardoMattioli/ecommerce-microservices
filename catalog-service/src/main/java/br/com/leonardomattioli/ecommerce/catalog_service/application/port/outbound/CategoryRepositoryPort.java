package br.com.leonardomattioli.ecommerce.catalog_service.application.port.outbound;

import br.com.leonardomattioli.ecommerce.catalog_service.domain.models.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepositoryPort {
    Category save(Category category);
    Optional<Category> findById(UUID id);
    List<Category> findAll();
    void deleteById(UUID id);
    Optional<Category> findByName(String name);
}