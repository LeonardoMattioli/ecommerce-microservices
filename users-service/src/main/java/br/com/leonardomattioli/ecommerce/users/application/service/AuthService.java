package br.com.leonardomattioli.ecommerce.users.application.service;

import br.com.leonardomattioli.ecommerce.users.domain.UseCase.UserRepository;
import br.com.leonardomattioli.ecommerce.users.domain.enums.Role;
import br.com.leonardomattioli.ecommerce.users.domain.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User register(String name, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Erro: E-mail já está em uso!");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password)); // Criptografa a senha
        user.setRole(Role.USER); // Padrão
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }
}