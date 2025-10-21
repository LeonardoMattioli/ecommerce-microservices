package br.com.leonardomattioli.ecommerce.users.infrastructure.persistence.adapters;

import br.com.leonardomattioli.ecommerce.users.domain.UseCase.UserRepository;
import br.com.leonardomattioli.ecommerce.users.domain.model.User;
import br.com.leonardomattioli.ecommerce.users.infrastructure.mapper.UserMapper;
import br.com.leonardomattioli.ecommerce.users.infrastructure.persistence.repository.JpaUserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaRepository;
    private final UserMapper userMapper;

    public UserRepositoryAdapter(JpaUserRepository jpaRepository, UserMapper userMapper) {
        this.jpaRepository = jpaRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        var entity = userMapper.toEntity(user);
        var savedEntity = jpaRepository.save(entity);
        return userMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(userMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}