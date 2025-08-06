package com.meuprojetotcc.autenticacao_diplomas.service;

import com.meuprojetotcc.autenticacao_diplomas.model.user.*;
import com.meuprojetotcc.autenticacao_diplomas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDto create(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }

        User user = new User();
        user.setNome(userDto.getNome());
        user.setApelido(userDto.getNome()); // Pode adaptar para campo específico
        user.setEmail(userDto.getEmail());
        user.setSenha(passwordEncoder.encode(userDto.getSenha()));
        user.setRole(userDto.getRole());

        userRepository.save(user);
        return new UserResponseDto(user);
    }

    public List<UserResponseDto> getAll() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    public Optional<UserResponseDto> getById(Long id) {
        return userRepository.findById(id).map(UserResponseDto::new);
    }

    public UserResponseDto atualizar(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setNome(userDto.getNome());
        user.setApelido(userDto.getNome());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());

        if (userDto.getSenha() != null && !userDto.getSenha().isBlank()) {
            user.setSenha(passwordEncoder.encode(userDto.getSenha()));
        }

        userRepository.save(user);
        return new UserResponseDto(user);
    }

    public void deletar(Long id) {
        userRepository.deleteById(id);
    }
}

