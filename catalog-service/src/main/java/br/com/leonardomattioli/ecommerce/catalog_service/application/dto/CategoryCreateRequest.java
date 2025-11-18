package br.com.leonardomattioli.ecommerce.catalog_service.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryCreateRequest(
        @NotBlank(message = "Category name cannot be blank")
        String name,

        @Size(max = 255, message = "Description cannot exceed 255 characters")
        String description
) {}