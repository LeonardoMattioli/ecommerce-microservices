package br.com.leonardomattioli.ecommerce.catalog_service.domain.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}