package br.com.leonardomattioli.ecommerce.users.infrastructure.persistence.adapters;

import br.com.leonardomattioli.ecommerce.users.domain.UseCase.RefreshTokenRepository;
import br.com.leonardomattioli.ecommerce.users.domain.model.RefreshToken;
import br.com.leonardomattioli.ecommerce.users.domain.model.User;
import br.com.leonardomattioli.ecommerce.users.infrastructure.mapper.RefreshTokenMapper;
import br.com.leonardomattioli.ecommerce.users.infrastructure.mapper.UserMapper;
import br.com.leonardomattioli.ecommerce.users.infrastructure.persistence.repository.JpaRefreshTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepository {

    private final JpaRefreshTokenRepository jpaRepository;
    private final RefreshTokenMapper refreshTokenMapper;
    private final UserMapper userMapper;

    public RefreshTokenRepositoryAdapter(JpaRefreshTokenRepository jpaRepository, 
                                         RefreshTokenMapper refreshTokenMapper, 
                                         UserMapper userMapper) {
        this.jpaRepository = jpaRepository;
        this.refreshTokenMapper = refreshTokenMapper;
        this.userMapper = userMapper;
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        var entity = refreshTokenMapper.toEntity(refreshToken);
        var savedEntity = jpaRepository.save(entity);
        return refreshTokenMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return jpaRepository.findByToken(token)
                .map(refreshTokenMapper::toDomain);
    }

    @Override
    public void deleteByUser(User user) {
        var userEntity = userMapper.toEntity(user);
        jpaRepository.deleteByUser(userEntity);
    }
}