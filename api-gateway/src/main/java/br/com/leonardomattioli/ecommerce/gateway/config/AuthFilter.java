package br.com.leonardomattioli.ecommerce.gateway.config;

import br.com.leonardomattioli.ecommerce.gateway.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    @Value("${app.security.public-routes}")
    private List<String> publicRoutes;

    public AuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        if (isPublicRoute(path)) {
            return chain.filter(exchange);
        }

        HttpHeaders headers = request.getHeaders();
        if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
            return unauthorized(exchange);
        }

        String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        String token = authHeader.substring(7); // Remove o "Bearer "

        try {
            // 3. Valida o token (assinatura e expiração)
            if (!jwtUtil.isTokenValid(token)) {
                return unauthorized(exchange); // Erro 401: Token inválido
            }

            // 4. (BÔNUS) Extrai o e-mail e passa para os microsserviços
            String email = jwtUtil.extractUsername(token);
            ServerHttpRequest modifiedRequest = request.mutate()
                    .header("X-User-Email", email) // Adiciona um header seguro
                    .build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());

        } catch (JwtException e) {
            return unauthorized(exchange);
        }
    }

    // Método auxiliar para verificar se a rota está na lista de rotas públicas
    private boolean isPublicRoute(String path) {
        return publicRoutes.stream().anyMatch(path::startsWith);
    }

    // Método auxiliar para retornar 401 Unauthorized
    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}