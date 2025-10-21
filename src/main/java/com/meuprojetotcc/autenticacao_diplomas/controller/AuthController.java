package com.meuprojetotcc.autenticacao_diplomas.controller;

import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.LoginEstudanteRequestDto;
import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.RegistroEstudanteRequestDto;
import com.meuprojetotcc.autenticacao_diplomas.model.user.LoginRequestUserDto;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.model.user.Role;
import com.meuprojetotcc.autenticacao_diplomas.model.user.UserResponseDto;
import com.meuprojetotcc.autenticacao_diplomas.repository.DiplomaRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.EstudanteRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.UserRepository;
import com.meuprojetotcc.autenticacao_diplomas.seguranca.JwtResponseDto;
import com.meuprojetotcc.autenticacao_diplomas.seguranca.JwtUtil;
import com.meuprojetotcc.autenticacao_diplomas.utils.TextoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EstudanteRepository estudanteRepository;

    @Autowired
    private DiplomaRepository diplomaRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // =================== Login Admin, Emissor e Ministério ===================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestUserDto loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha())
            );
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token = jwtUtil.generateToken(user.getEmail(), "ROLE_" + user.getRole().name());

        return ResponseEntity.ok(new JwtResponseDto(token, "ROLE_" + user.getRole().name()));
    }

    // =================== Login Estudante ===================

    @PostMapping("/login-estudante")
    public ResponseEntity<?> loginEstudante(@RequestBody Estudante loginRequest) {
        Optional<Estudante> estudanteOpt = estudanteRepository.findByNumeroMatricula(loginRequest.getNumeroMatricula());

        if (estudanteOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Número de matrícula não encontrado.");
        }

        Estudante estudante = estudanteOpt.get();

        if (!estudante.getSenha().equals(loginRequest.getSenha()) ||
                !estudante.getNomeCompleto().equalsIgnoreCase(loginRequest.getNomeCompleto())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Nome ou senha incorretos.");
        }

        // Se tudo certo, retorna o estudante e seus diplomas
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("nome", estudante.getNomeCompleto());
        resposta.put("numeroMatricula", estudante.getNumeroMatricula());
        resposta.put("id", estudante.getId());

        return ResponseEntity.ok(resposta);
    }



    @PostMapping("/registro-estudante")
    public ResponseEntity<?> registrarEstudante(@RequestBody RegistroEstudanteRequestDto request) {

        if (estudanteRepository.existsByNumeroMatricula(request.getNumeroMatricula())) {
            return ResponseEntity.badRequest().body("Estudante já cadastrado com este número de matrícula");
        }

        Estudante estudante = new Estudante();
        estudante.setNomeCompleto(request.getNomeCompleto());
        estudante.setEmail(request.getEmail());
        estudante.setNumeroMatricula(request.getNumeroMatricula());
        estudante.setDataNascimento(request.getDataNascimento());
        estudante.setGenero(request.getGenero());
        estudante.setSenha(passwordEncoder.encode(request.getSenha()));

        estudanteRepository.save(estudante);

        return ResponseEntity.ok("Estudante registrado com sucesso!");
    }


    // =================== Registrar Usuário (somente Admin) ===================
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registro-user")
    public ResponseEntity<?> registrarUsuario(@RequestBody UserResponseDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }

        User user = new User();
        user.setNome(userRequestDto.getNome());
        user.setApelido(userRequestDto.getApelido());
        user.setEmail(userRequestDto.getEmail());
        user.setSenha(passwordEncoder.encode(userRequestDto.getSenha()));
        user.setRole(userRequestDto.getRole()); // Pode ser ADMIN, EMISSOR ou MINISTERIO

        userRepository.save(user);

        return ResponseEntity.ok(new UserResponseDto(user));
    }

    // =================== Método para verificar diploma de estudante (placeholder) ===================
    private boolean verificarDiplomaParaMatricula(String numeroMatricula) {
        // TODO: Implementar consulta real ao repositório/serviço de diplomas
        return true; // Por enquanto assume que sempre tem diploma
    }
}
