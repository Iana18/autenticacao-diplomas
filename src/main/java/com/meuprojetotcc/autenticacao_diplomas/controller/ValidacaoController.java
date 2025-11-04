package com.meuprojetotcc.autenticacao_diplomas.controller;

import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import com.meuprojetotcc.autenticacao_diplomas.service.ValidacaoService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/validacao")
@CrossOrigin(origins = "*")
public class ValidacaoController {


    private final ValidacaoService validacaoService;

    // pega a chave secreta do JWT configurada no application.properties

    private String jwtSecret;

    public ValidacaoController(ValidacaoService validacaoService) {
        this.validacaoService = validacaoService;
    }

    @GetMapping("/diploma")
    public ResponseEntity<?> validar(
            @RequestHeader("Authorization") String token,
            @RequestParam String hash,
            @RequestParam String numeroMatricula) {

        try {
            // remove o prefixo "Bearer "
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // decodifica o JWT para extrair o ID do usuário (verificador)
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret.getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            Long verificadorId = Long.parseLong(claims.get("id").toString());

            // chama o serviço de validação
            Diploma diploma = validacaoService.validarDiploma(hash, numeroMatricula, verificadorId);

            // retorna JSON com todos os dados
            return ResponseEntity.ok(Map.of(
                    "status", "Válido ✅",
                    "mensagem", "Diploma verificado com sucesso.",
                    "dados", Map.of(
                            "nomeEstudante", diploma.getEstudante().getNomeCompleto(),
                            "numeroMatricula", diploma.getEstudante().getNumeroMatricula(),
                            "curso", diploma.getCurso().getNome(),
                            "instituicao", diploma.getInstituicao().getNome(),
                            "grauAcademico", diploma.getGrauAcademico().toString(),
                            "dataConclusao", diploma.getDataConclusao(),
                            "dataEmissao", diploma.getDataEmissao(),
                            "hashBlockchain", diploma.getHashBlockchain(),
                            "enderecoTransacao", diploma.getEnderecoTransacao()
                    )
            ));

        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of(
                    "status", "Inválido ❌",
                    "mensagem", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of(
                    "status", "Erro ❌",
                    "mensagem", "Token inválido ou expirado."
            ));
        }

    }
}