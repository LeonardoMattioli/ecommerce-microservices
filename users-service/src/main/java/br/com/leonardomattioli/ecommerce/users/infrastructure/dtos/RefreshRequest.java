package br.com.leonardomattioli.ecommerce.users.infrastructure.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshRequest {
    @NotBlank(message = "O refresh token é obrigatório")
    private String refreshToken;
}