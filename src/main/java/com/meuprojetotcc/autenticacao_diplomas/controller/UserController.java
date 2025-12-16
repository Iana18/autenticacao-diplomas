package com.meuprojetotcc.autenticacao_diplomas.controller;

import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.model.user.UserDto;
import com.meuprojetotcc.autenticacao_diplomas.model.user.UserResponseDto;
import com.meuprojetotcc.autenticacao_diplomas.repository.UserRepository;
import com.meuprojetotcc.autenticacao_diplomas.seguranca.JwtUtil;
import com.meuprojetotcc.autenticacao_diplomas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/criar")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.create(userDto));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return userService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.atualizar(id, userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // ===========================
    // Endpoint para retornar o usuário logado
    // ===========================
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> me(@RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.replace("Bearer ", "");
        String email = jwtUtil.extractUsername(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Converte para DTO
        UserResponseDto dto = new UserResponseDto(user);

        return ResponseEntity.ok(dto);
    }

}
