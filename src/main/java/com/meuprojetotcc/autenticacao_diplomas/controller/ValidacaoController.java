package com.meuprojetotcc.autenticacao_diplomas.controller;

import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.model.verificacao.Verificacao;
import com.meuprojetotcc.autenticacao_diplomas.service.DiplomaService;
import com.meuprojetotcc.autenticacao_diplomas.service.UserService;
import com.meuprojetotcc.autenticacao_diplomas.service.VerificacaoService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import static java.util.Map.entry;

@RestController
@RequestMapping("/api/validacao")
@CrossOrigin(origins = "*")
public class ValidacaoController {

    private final VerificacaoService verificacaoService;
    private final DiplomaService diplomaService;
    private final UserService userService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public ValidacaoController(VerificacaoService verificacaoService,
                               DiplomaService diplomaService,
                               UserService userService) {
        this.verificacaoService = verificacaoService;
        this.diplomaService = diplomaService;
        this.userService = userService;
    }

    @GetMapping("/diploma-publico")
    public ResponseEntity<?> validarDiplomaPublico(
            @RequestHeader("Authorization") String token,
            @RequestParam String numeroDiploma,
            @RequestParam String hash) {

        try {
            // Remove "Bearer " do token
            if (token.startsWith("Bearer ")) token = token.substring(7);

            // Lê claims do JWT
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret.getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            // Pega o email do usuário logado
            String email = claims.get("sub", String.class);
            User verificador = userService.buscarUsuarioPorEmail(email);

            // Busca diploma pelo número e hash
            Diploma diploma = diplomaService.buscarPorNumeroEHash(numeroDiploma, hash);
            if (diploma == null) {
                throw new RuntimeException("Diploma não encontrado");
            }

            // Cria registro da verificação
            Verificacao verificacao = verificacaoService.criarVerificacao(diploma.getId(), verificador.getId());

            // Retorna resposta com dados do diploma usando Map.ofEntries
            return ResponseEntity.ok(Map.ofEntries(
                    entry("status", "Válido ✅"),
                    entry("mensagem", "Diploma verificado com sucesso."),
                    entry("dados", Map.ofEntries(
                            entry("nomeEstudante", diploma.getEstudante().getNomeCompleto()),
                            entry("numeroDiploma", diploma.getNumeroDiploma()),
                            entry("curso", diploma.getCurso().getNome()),
                            entry("instituicao", diploma.getInstituicao().getNome()),
                            entry("grauAcademico", diploma.getGrauAcademico().toString()),
                            entry("notaFinal", diploma.getNotaFinal()),
                            entry("cargaHoraria", diploma.getCargaHoraria()),
                            entry("assinaturaInstituicao", diploma.getAssinaturaInstituicao()),
                            entry("carimboInstituicao", diploma.getCarimboInstituicao()),
                            entry("dataConclusao", diploma.getDataConclusao()),
                            entry("dataEmissao", diploma.getDataEmissao()),
                            entry("hashBlockchain", diploma.getHashBlockchain()),
                            entry("enderecoTransacao", diploma.getEnderecoTransacao())
                    ))
            ));

        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.ofEntries(
                    entry("status", "Inválido ❌"),
                    entry("mensagem", e.getMessage())
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.ofEntries(
                    entry("status", "Erro ❌"),
                    entry("mensagem", "Token inválido ou expirado.")
            ));
        }
    }
}
