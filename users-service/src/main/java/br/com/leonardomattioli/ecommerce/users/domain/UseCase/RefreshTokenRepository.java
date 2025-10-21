package br.com.leonardomattioli.ecommerce.users.domain.UseCase;

import br.com.leonardomattioli.ecommerce.users.domain.model.RefreshToken;
import br.com.leonardomattioli.ecommerce.users.domain.model.User;

import java.util.Optional;

public interface RefreshTokenRepository {

    RefreshToken save(RefreshToken refreshToken);

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);
}