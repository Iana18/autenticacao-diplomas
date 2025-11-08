package com.meuprojetotcc.autenticacao_diplomas.controller;

import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.AtivacaoRequestDto;
import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.EstudanteDto;
import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.LoginEstudanteRequestDto;
import com.meuprojetotcc.autenticacao_diplomas.seguranca.JwtResponseDto;
import com.meuprojetotcc.autenticacao_diplomas.seguranca.JwtUtil;
import com.meuprojetotcc.autenticacao_diplomas.service.EstudanteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudantes")
@CrossOrigin(origins = "*")
public class EstudanteController {

    private final EstudanteService estudanteService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public EstudanteController(EstudanteService estudanteService,
                               PasswordEncoder passwordEncoder,
                               JwtUtil jwtUtil) {
        this.estudanteService = estudanteService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ================= Cadastro pelo emissor (sem senha) =================
    // 1) Cadastro pelo emissor (só ADMIN deve acessar esse endpoint)
    @PostMapping("/cadastrar-emissor")
    public ResponseEntity<?> cadastrarPeloEmissor(@RequestBody EstudanteDto dto) {
        try {
            Estudante criado = estudanteService.cadastrarEstudantePeloEmissor(dto);
            return ResponseEntity.ok(criado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // 2) Ativação: endpoint público que o estudante acessa via link
    // Exemplo: POST /estudantes/ativar?token=abc123  body: { "senha": "novaSenha123" }
    @PostMapping("/ativar")
    public ResponseEntity<?> ativarConta(@RequestParam String token, @RequestBody AtivacaoRequestDto dto) {
        boolean ok = estudanteService.ativarConta(token, dto.getSenha());
        if (!ok) return ResponseEntity.badRequest().body("Token inválido ou conta já ativada");
        return ResponseEntity.ok("Conta ativada com sucesso. Você já pode fazer login.");
    }

    // ================= Registro público (com senha) =================
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarPublico(@RequestParam String nomeCompleto,
                                              @RequestParam String numeroMatricula,
                                              @RequestParam String senha) {
        try {
            Estudante estudante = estudanteService.registrarEstudante(nomeCompleto, numeroMatricula, senha);
            return ResponseEntity.ok(estudante);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ================= Login =================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginEstudanteRequestDto dto) {
        // Busca estudante pela matrícula (lança exceção se não achar)
        Estudante estudante = estudanteService.buscarPorNumeroMatricula(dto.getNumeroMatricula());

        // Verifica senha
        if (!passwordEncoder.matches(dto.getSenha(), estudante.getSenha())) {
            return ResponseEntity.badRequest().body("Senha incorreta");
        }

        // Gera o token JWT
        String token = jwtUtil.generateToken(estudante);

        // Retorna token + tipo de usuário
        return ResponseEntity.ok(new JwtResponseDto(token, "ESTUDANTE"));
    }


    // ================= Alterar senha =================
    @PutMapping("/{id}/senha")
    public ResponseEntity<String> alterarSenha(@PathVariable Long id,
                                               @RequestBody AtivacaoRequestDto dto) {
        try {
            estudanteService.alterarSenha(id, dto);
            return ResponseEntity.ok("Senha alterada com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ================= CRUD básico =================
    @GetMapping("/{id}")
    public ResponseEntity<Estudante> buscarPorId(@PathVariable Long id) {
        return estudanteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Estudante>> buscarPorNome(@RequestParam String nome) {
        List<Estudante> estudantes = estudanteService.buscarPorNome(nome);
        return ResponseEntity.ok(estudantes);
    }

    @GetMapping
    public ResponseEntity<List<Estudante>> listarTodos() {
        return ResponseEntity.ok(estudanteService.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudante> atualizarEstudante(@PathVariable Long id,
                                                        @RequestBody Estudante estudante) {
        try {
            Estudante atualizado = estudanteService.atualizarEstudante(id, estudante);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEstudante(@PathVariable Long id) {
        estudanteService.deletarEstudante(id);
        return ResponseEntity.noContent().build();
    }
}
