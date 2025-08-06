package com.meuprojetotcc.autenticacao_diplomas.controller;


import com.meuprojetotcc.autenticacao_diplomas.model.notificacao.Notificacao;
import com.meuprojetotcc.autenticacao_diplomas.model.notificacao.NotificacaoUpdateDTO;
import com.meuprojetotcc.autenticacao_diplomas.service.NotificacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificacoes")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    public NotificacaoController(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Notificacao>> listarNotificacoesPorUsuario(@PathVariable Long usuarioId) {
        List<Notificacao> notificacoes = notificacaoService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(notificacoes);
    }

    @PostMapping
    public ResponseEntity<Notificacao> criarNotificacao(@RequestBody Notificacao notificacao) {
        Notificacao novaNotificacao = notificacaoService.criarNotificacao(notificacao);
        return ResponseEntity.ok(novaNotificacao);
    }

    @PutMapping("/{notificacaoId}")
    public ResponseEntity<Notificacao> atualizarNotificacao(
            @PathVariable Long notificacaoId,
            @RequestBody NotificacaoUpdateDTO notificacaoUpdateDTO) {

        Notificacao notifAtualizada = notificacaoService.atualizarNotificacao(
                notificacaoId,
                notificacaoUpdateDTO.getMensagem(),
                notificacaoUpdateDTO.getLida());

        return ResponseEntity.ok(notifAtualizada);
    }

    @DeleteMapping("/{notificacaoId}")
    public ResponseEntity<Void> deletarNotificacao(@PathVariable Long notificacaoId) {
        notificacaoService.deletarNotificacao(notificacaoId);
        return ResponseEntity.noContent().build();
    }
}
