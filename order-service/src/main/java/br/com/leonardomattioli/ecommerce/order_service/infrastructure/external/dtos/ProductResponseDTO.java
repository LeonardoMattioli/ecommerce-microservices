package br.com.leonardomattioli.ecommerce.order_service.infrastructure.external.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDTO(UUID id, BigDecimal price) {}