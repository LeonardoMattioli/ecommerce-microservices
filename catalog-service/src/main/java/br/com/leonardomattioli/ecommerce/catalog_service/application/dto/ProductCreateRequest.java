package br.com.leonardomattioli.ecommerce.catalog_service.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductCreateRequest(
    @NotBlank String name,
    String description,
    @NotNull @Positive BigDecimal price,
    @NotBlank String sku,
    String imageUrl,
    @NotNull UUID categoryId
) {}