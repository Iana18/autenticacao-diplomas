package com.meuprojetotcc.autenticacao_diplomas.controller;


import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.repository.EstudanteRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.UserRepository;
import com.meuprojetotcc.autenticacao_diplomas.seguranca.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EstudanteRepository estudanteRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Login para Admin e Emissor
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha())
        );

        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return ResponseEntity.ok(new JwtResponseDto(token, "ROLE_" + user.getRole().name()));
    }

    // Login para Estudante
    @PostMapping("/login-estudante")
    public ResponseEntity<JwtResponseDto> loginEstudante(@RequestBody LoginEstudanteRequestDto request) {
        Estudante estudante = estudanteRepository
                .findByNomeCompletoAndNumeroMatricula(request.getNomeCompleto(), request.getNumeroMatricula())
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado"));

        // Como matrícula parece ser senha em texto puro, compara simples:
        if (!request.getNumeroMatricula().equals(estudante.getNumeroMatricula())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = jwtUtil.generateToken(estudante.getEmail(), "ESTUDANTE");

        return ResponseEntity.ok(new JwtResponseDto(token, "ROLE_ESTUDANTE"));
    }

}
