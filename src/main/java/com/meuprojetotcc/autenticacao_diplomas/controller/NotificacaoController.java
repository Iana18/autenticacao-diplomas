package com.meuprojetotcc.autenticacao_diplomas.controller;

import com.meuprojetotcc.autenticacao_diplomas.model.notificacao.Notificacao;
import com.meuprojetotcc.autenticacao_diplomas.service.NotificacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    private final NotificacaoService service;

    public NotificacaoController(NotificacaoService service) {
        this.service = service;
    }

    @PostMapping("/criar/{usuarioId}")
    public ResponseEntity<Notificacao> criar(@PathVariable Long usuarioId, @RequestBody String mensagem) {
        Notificacao n = service.criar(usuarioId, mensagem);
        return ResponseEntity.ok(n);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Notificacao>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<Notificacao> list = service.listarPorUsuario(usuarioId);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @PutMapping("/marcar-lida/{id}")
    public ResponseEntity<Void> marcarLida(@PathVariable Long id) {
        service.marcarComoLida(id);
        return ResponseEntity.noContent().build();
    }
}
