package br.com.leonardomattioli.ecommerce.catalog_service.application.dto;

import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name,
        String description
) {}