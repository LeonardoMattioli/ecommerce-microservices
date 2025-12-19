package br.com.leonardomattioli.ecommerce.catalog_service.infrastructure.persistence.repository;

import br.com.leonardomattioli.ecommerce.catalog_service.infrastructure.persistence.entity.CategoryEntity;
import br.com.leonardomattioli.ecommerce.catalog_service.infrastructure.persistence.entity.ProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    private ProductJpaRepository productRepository;
    
    @Autowired
    private CategoryJpaRepository categoryRepository; 

    @Test
    @DisplayName("Should save and find product by ID successfully")
    void shouldSaveAndFindProduct() {
        CategoryEntity category = CategoryEntity.builder()
                .name("Eletrônicos")
                .description("Gadgets diversos")
                .build();
        categoryRepository.save(category);

        UUID productId = UUID.randomUUID();
        ProductEntity newProduct = ProductEntity.builder()
                .name("Nintendo Switch")
                .description("Console híbrido")
                .price(new BigDecimal("2000.00"))
                .sku("NIN-SW-001")
                .imageUrl("http://img.com/switch.png")
                .category(category)
                .build();

        ProductEntity savedProduct = productRepository.save(newProduct);

        Optional<ProductEntity> foundProduct = productRepository.findById(savedProduct.getId());

        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("Nintendo Switch");
    }
}