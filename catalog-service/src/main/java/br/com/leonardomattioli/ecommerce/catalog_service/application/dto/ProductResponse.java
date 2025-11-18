package br.com.leonardomattioli.ecommerce.catalog_service.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
    UUID id,
    String name,
    String description,
    BigDecimal price,
    String sku,
    String imageUrl,
    String categoryName
) {}