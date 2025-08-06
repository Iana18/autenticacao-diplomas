package com.meuprojetotcc.autenticacao_diplomas.controller;

import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.LoginEstudanteRequestDto;
import com.meuprojetotcc.autenticacao_diplomas.model.user.LoginRequestUserDto;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.model.user.UserResponseDto;
import com.meuprojetotcc.autenticacao_diplomas.repository.EstudanteRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.UserRepository;
import com.meuprojetotcc.autenticacao_diplomas.seguranca.JwtResponseDto;
import com.meuprojetotcc.autenticacao_diplomas.seguranca.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    public ResponseEntity<?> login(@RequestBody LoginRequestUserDto loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha())
            );
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }

        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return ResponseEntity.ok(new JwtResponseDto(token, "ROLE_" + user.getRole().name()));
    }

    // Login para Estudante
    @PostMapping("/login-estudante")
    public ResponseEntity<?> loginEstudante(@RequestBody LoginEstudanteRequestDto request) {
        Estudante estudante = estudanteRepository
                .findByNumeroMatricula(request.getNumeroMatricula())
                .orElse(null);

        if (estudante == null) {
            return ResponseEntity.status(404).body("Estudante não encontrado");
        }

        if (!passwordEncoder.matches(request.getSenha(), estudante.getSenha())) {
            return ResponseEntity.status(401).body("Senha inválida");
        }

        // Aqui você deve implementar a lógica para verificar se o diploma existe
        boolean temDiploma = verificarDiplomaParaMatricula(estudante.getNumeroMatricula());
        if (!temDiploma) {
            return ResponseEntity.status(404).body("Diploma não encontrado para esta matrícula");
        }

        String token = jwtUtil.generateToken(estudante.getEmail(), "ESTUDANTE");

        return ResponseEntity.ok(new JwtResponseDto(token, "ROLE_ESTUDANTE"));
    }

    // Registro de Admin e Emissor — só Admin pode criar
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registro-user")
    public ResponseEntity<?> registrarUsuario(@RequestBody UserResponseDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }

        User user = new User();
        user.setNome(userRequestDto.getNome());
        user.setEmail(userRequestDto.getEmail());
        user.setSenha(passwordEncoder.encode(userRequestDto.getSenha()));
        user.setRole(userRequestDto.getRole());

        userRepository.save(user);

        return ResponseEntity.ok(new UserResponseDto(user));
    }

    // Método placeholder para verificar se o diploma existe para o estudante
    private boolean verificarDiplomaParaMatricula(String numeroMatricula) {
        // TODO: Implemente a consulta real ao repositório/serviço de diplomas
        // Exemplo:
        // return diplomaRepository.existsByNumeroMatricula(numeroMatricula);
        return true; // Por enquanto, assume que sempre tem diploma
    }
}
