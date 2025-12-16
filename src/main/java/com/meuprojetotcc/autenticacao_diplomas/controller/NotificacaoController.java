package com.meuprojetotcc.autenticacao_diplomas.controller;

import com.meuprojetotcc.autenticacao_diplomas.model.notificacao.CriarNotificacaoDTO;
import com.meuprojetotcc.autenticacao_diplomas.model.notificacao.Notificacao;
import com.meuprojetotcc.autenticacao_diplomas.model.notificacao.NotificacaoDto;
import com.meuprojetotcc.autenticacao_diplomas.model.notificacao.NotificacaoRequest;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.repository.UserRepository;
import com.meuprojetotcc.autenticacao_diplomas.service.NotificacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/api/notificacoes")
@CrossOrigin(origins = "*")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    public NotificacaoController(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    // Admin → global ou específico
    @PostMapping("/admin")
    public ResponseEntity<List<NotificacaoDto>> enviarAdmin(@RequestBody NotificacaoRequest request) {
        List<Notificacao> lista = notificacaoService.enviarNotificacaoAdmin(request);
        return ResponseEntity.ok(lista.stream().map(notificacaoService::toDto).collect(Collectors.toList()));
    }

    // Emissor → apenas estudantes
    @PostMapping("/emissor")
    public ResponseEntity<List<NotificacaoDto>> enviarEmissor(@RequestBody NotificacaoRequest request) {
        List<Notificacao> lista = notificacaoService.enviarNotificacaoEmissor(request);
        return ResponseEntity.ok(lista.stream().map(notificacaoService::toDto).collect(Collectors.toList()));
    }
}