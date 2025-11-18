package br.com.leonardomattioli.ecommerce.catalog_service.infrastructure.persistence.repository;

import br.com.leonardomattioli.ecommerce.catalog_service.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, UUID> {

    Optional<CategoryEntity> findByName(String name);
}
