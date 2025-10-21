package br.com.leonardomattioli.ecommerce.users.application.service;

import br.com.leonardomattioli.ecommerce.users.domain.UseCase.RefreshTokenRepository;
import br.com.leonardomattioli.ecommerce.users.domain.UseCase.UserRepository;
import br.com.leonardomattioli.ecommerce.users.domain.model.RefreshToken;
import br.com.leonardomattioli.ecommerce.users.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${jwt.refresh-expiration-ms}")
    private long REFRESH_EXPIRATION_MS;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public RefreshToken createRefreshToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiresAt(Instant.now().plusMillis(REFRESH_EXPIRATION_MS));
        refreshToken.setToken(Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes()));
        refreshToken.setRevoked(false);

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiresAt().isBefore(Instant.now())) {
            token.setRevoked(true);
            refreshTokenRepository.save(token);
            throw new RuntimeException("Refresh token expirado. Por favor, faça login novamente.");
        }
        if (token.isRevoked()) {
            throw new RuntimeException("Refresh token revogado. Por favor, faça login novamente.");
        }
        return token;
    }
}