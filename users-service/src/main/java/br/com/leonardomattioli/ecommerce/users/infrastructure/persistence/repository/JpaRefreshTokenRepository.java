package br.com.leonardomattioli.ecommerce.users.infrastructure.persistence.repository;

import br.com.leonardomattioli.ecommerce.users.infrastructure.persistence.entity.RefreshTokenEntity;
import br.com.leonardomattioli.ecommerce.users.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaRefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {

    Optional<RefreshTokenEntity> findByToken(String token);

    void deleteByUser(UserEntity user);
}