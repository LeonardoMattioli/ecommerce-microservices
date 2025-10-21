package br.com.leonardomattioli.ecommerce.users.infrastructure.mapper;

import br.com.leonardomattioli.ecommerce.users.domain.model.RefreshToken;
import br.com.leonardomattioli.ecommerce.users.infrastructure.persistence.entity.RefreshTokenEntity;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenMapper {

    private final UserMapper userMapper;

    public RefreshTokenMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public RefreshToken toDomain(RefreshTokenEntity entity) {
        return new RefreshToken(
                entity.getId(),
                userMapper.toDomain(entity.getUser()),
                entity.getToken(),
                entity.isRevoked(),
                entity.getExpiresAt()
        );
    }

    public RefreshTokenEntity toEntity(RefreshToken domain) {
        return RefreshTokenEntity.builder()
                .id(domain.getId())
                .user(userMapper.toEntity(domain.getUser()))
                .token(domain.getToken())
                .revoked(domain.isRevoked())
                .expiresAt(domain.getExpiresAt())
                .build();
    }
}