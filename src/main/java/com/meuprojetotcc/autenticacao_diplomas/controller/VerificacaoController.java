package com.meuprojetotcc.autenticacao_diplomas.controller;

import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.model.verificacao.Verificacao;
import com.meuprojetotcc.autenticacao_diplomas.service.UserService;
import com.meuprojetotcc.autenticacao_diplomas.service.VerificacaoService;
import com.meuprojetotcc.autenticacao_diplomas.service.DiplomaService; // serviço para buscar diploma
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/verificacoes")
@CrossOrigin(origins = "*")
public class VerificacaoController {

    private final VerificacaoService verificacaoService;
    private final DiplomaService diplomaService;
    private  final UserService userService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public VerificacaoController(VerificacaoService verificacaoService,
                                 DiplomaService diplomaService, UserService userService) {
        this.verificacaoService = verificacaoService;
        this.diplomaService = diplomaService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Verificacao>> listarTodas() {
        return ResponseEntity.ok(verificacaoService.listarTodas());
    }

    @PostMapping("/diploma")
    public ResponseEntity<?> validarDiploma(
            @RequestHeader("Authorization") String token,
            @RequestParam String numeroDiploma,
            @RequestParam String hash) {

        try {
            if (token.startsWith("Bearer "))
                token = token.substring(7);

            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret.getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.get("email", String.class);

            // Buscar usuário logado pelo email no UserService
            User verificador = userService.buscarUsuarioPorEmail(email); // <-- você precisa ter UserService

            // Buscar diploma pelo número e hash no DiplomaService
            Diploma diploma = diplomaService.buscarPorNumeroEHash(numeroDiploma, hash);

            // Registrar a verificação usando criarVerificacao (do seu VerificacaoService)
            Verificacao verificacao = verificacaoService.criarVerificacao(diploma.getId(), verificador.getId());

            return ResponseEntity.ok(verificacao);

        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(
                    new ApiResponse("Inválido ❌", e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.status(401).body(
                    new ApiResponse("Erro ❌", "Token inválido ou expirado.")
            );
        }
    }


    // DTO para respostas de erro
    static class ApiResponse {
        private String status;
        private String mensagem;

        public ApiResponse(String status, String mensagem) {
            this.status = status;
            this.mensagem = mensagem;
        }

        public String getStatus() { return status; }
        public String getMensagem() { return mensagem; }
    }
}




