package br.com.leonardomattioli.ecommerce.order_service.infrastructure.controllers;

import br.com.leonardomattioli.ecommerce.order_service.application.dto.OrderCreateRequest;
import br.com.leonardomattioli.ecommerce.order_service.application.dto.OrderResponse;
import br.com.leonardomattioli.ecommerce.order_service.application.ports.inbound.CreateOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private final CreateOrderUseCase createOrderUseCase;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderCreateRequest request) {
        OrderResponse response = createOrderUseCase.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}