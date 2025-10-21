package com.meuprojetotcc.autenticacao_diplomas.controller;

import com.meuprojetotcc.autenticacao_diplomas.model.verificacao.Verificacao;
import com.meuprojetotcc.autenticacao_diplomas.service.VerificacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/verificacoes")
@CrossOrigin(origins = "*")
public class VerificacaoController {

    private final VerificacaoService verificacaoService;

    public VerificacaoController(VerificacaoService verificacaoService) {
        this.verificacaoService = verificacaoService;
    }

    @GetMapping
    public ResponseEntity<List<Verificacao>> listarTodas() {
        return ResponseEntity.ok(verificacaoService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Verificacao> buscarPorId(@PathVariable Long id) {
        return verificacaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Verificacao> criarVerificacao(@RequestParam Long certificadoId,
                                                        @RequestParam Long verificadorId) {
        Verificacao verificacao = verificacaoService.criarVerificacao(certificadoId, verificadorId);
        return ResponseEntity.ok(verificacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Verificacao> atualizarVerificacao(@PathVariable Long id,
                                                            @RequestParam(required = false) Long certificadoId,
                                                            @RequestParam(required = false) Long verificadorId,
                                                            @RequestParam(required = false) String dataVerificacao) {
        LocalDateTime novaData = null;
        if (dataVerificacao != null) {
            novaData = LocalDateTime.parse(dataVerificacao);
        }
        Verificacao verificacaoAtualizada = verificacaoService.atualizarVerificacao(id, certificadoId, verificadorId, novaData);
        return ResponseEntity.ok(verificacaoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVerificacao(@PathVariable Long id) {
        verificacaoService.deletarVerificacao(id);
        return ResponseEntity.noContent().build();
    }
}
