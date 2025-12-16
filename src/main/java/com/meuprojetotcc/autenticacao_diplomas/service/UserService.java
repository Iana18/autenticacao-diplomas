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
        user.setApelido(userDto.getApelido()); // Pode adaptar para campo específico
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

        // Atualiza campos corretamente
        if (userDto.getNome() != null) {
            user.setNome(userDto.getNome());
        }

        if (userDto.getApelido() != null) {
            user.setApelido(userDto.getApelido());
        }

        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }

        if (userDto.getRole() != null) {
            user.setRole(userDto.getRole());
        }

        // Atualiza senha apenas se foi enviada
        if (userDto.getSenha() != null && !userDto.getSenha().isBlank()) {
            user.setSenha(passwordEncoder.encode(userDto.getSenha()));
        }

        userRepository.save(user);
        return new UserResponseDto(user);
    }

    public void deletar(Long id) {
        userRepository.deleteById(id);
    }


    public User buscarUsuarioPorEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }


    public UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(user); // usa o construtor que já existe
    }
}

