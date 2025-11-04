package com.meuprojetotcc.autenticacao_diplomas.controller;

import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.EstudanteDto;
import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.LoginEstudanteRequestDto;
import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.RegistroEstudanteRequestDto;
import com.meuprojetotcc.autenticacao_diplomas.model.user.LoginRequestUserDto;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.model.user.UserDto;
import com.meuprojetotcc.autenticacao_diplomas.model.user.UserResponseDto;
import com.meuprojetotcc.autenticacao_diplomas.repository.DiplomaRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.EstudanteRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.UserRepository;
import com.meuprojetotcc.autenticacao_diplomas.seguranca.CustomUserDetailsService;
import com.meuprojetotcc.autenticacao_diplomas.seguranca.JwtResponseDto;
import com.meuprojetotcc.autenticacao_diplomas.seguranca.JwtUtil;
import com.meuprojetotcc.autenticacao_diplomas.seguranca.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    private CustomUserDetailsService customUserDetailsService;


    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private DiplomaRepository diplomaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ===================== LOGIN =====================

    // Login para usuário normal
    @PostMapping("/login-user")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestUserDto request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getEmail());

        String token = jwtUtil.generateToken(userDetails);

        String role = "USER";
        if (userDetails instanceof User u) {
            role = u.getRole().name();
        }

        return ResponseEntity.ok(new JwtResponseDto(token, role));
    }

    // Login para estudante
    @PostMapping("/login-estudante")
    public ResponseEntity<?> loginEstudante(@RequestBody LoginEstudanteRequestDto request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getNumeroMatricula(), request.getSenha())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getNumeroMatricula());

        String token = jwtUtil.generateToken(userDetails);

        boolean possuiDiploma = diplomaRepository.existsByEstudante_NumeroMatricula(request.getNumeroMatricula());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("role", "ESTUDANTE");
        response.put("possuiDiploma", possuiDiploma);

        return ResponseEntity.ok(response);
    }


    // ===================== REGISTRO =====================
    @PostMapping("/registro-estudante")
    public ResponseEntity<?> registrarEstudante(@RequestBody RegistroEstudanteRequestDto request) {
        if (estudanteRepository.existsByNumeroMatricula(request.getNumeroMatricula())) {
            return ResponseEntity.badRequest().body("Número de matrícula já cadastrado");
        }
        if (estudanteRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }

        Estudante estudante = new Estudante();
        estudante.setNomeCompleto(request.getNomeCompleto());
        estudante.setEmail(request.getEmail());
        estudante.setNumeroMatricula(request.getNumeroMatricula());
        estudante.setDataNascimento(request.getDataNascimento());
        estudante.setGenero(request.getGenero());
        estudante.setSenha(passwordEncoder.encode(request.getSenha()));

        estudanteRepository.save(estudante);

        // Retorna apenas DTO seguro, sem expor senha ou listas
        EstudanteDto estudanteDto = new EstudanteDto();
        estudanteDto.setId(estudante.getId());
        estudanteDto.setNomeCompleto(estudante.getNomeCompleto());
        estudanteDto.setEmail(estudante.getEmail());
        estudanteDto.setNumeroMatricula(estudante.getNumeroMatricula());
        estudanteDto.setDataNascimento(estudante.getDataNascimento());
        estudanteDto.setGenero(estudante.getGenero());

        return ResponseEntity.ok(estudanteDto);
    }


    @PostMapping("/registro-user")
    public ResponseEntity<?> registrarUsuario(@RequestBody UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }

        User user = new User();
        user.setNome(userDto.getNome());
        user.setApelido(userDto.getApelido());
        user.setEmail(userDto.getEmail());
        user.setSenha(passwordEncoder.encode(userDto.getSenha()));
        user.setRole(userDto.getRole());

        userRepository.save(user);

        return ResponseEntity.ok(new UserResponseDto(user)); // ✅ Retornando DTO seguro
    }



    // =================== Método para verificar diploma de estudante (placeholder) ===================
    private boolean verificarDiplomaParaMatricula(String numeroMatricula) {
        // TODO: Implementar consulta real ao repositório/serviço de diplomas
        return diplomaRepository.existsByEstudante_NumeroMatricula(numeroMatricula);
    }

}
