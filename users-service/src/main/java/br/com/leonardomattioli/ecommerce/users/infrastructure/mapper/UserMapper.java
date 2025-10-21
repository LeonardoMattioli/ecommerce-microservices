package br.com.leonardomattioli.ecommerce.users.infrastructure.mapper;

import br.com.leonardomattioli.ecommerce.users.domain.model.User;
import br.com.leonardomattioli.ecommerce.users.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDomain(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setEmail(userEntity.getEmail());
        user.setName(userEntity.getName());
        user.setPasswordHash(userEntity.getPasswordHash());
        user.setCreatedAt(userEntity.getCreatedAt());
        user.setRole(userEntity.getRole());
        return user;
    }

    public UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .passwordHash(user.getPasswordHash())
                .createdAt(user.getCreatedAt())
                .role(user.getRole())
                .build();
    }
}
