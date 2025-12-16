package com.meuprojetotcc.autenticacao_diplomas.service;

import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.model.notificacao.Notificacao;
import com.meuprojetotcc.autenticacao_diplomas.model.notificacao.NotificacaoDto;
import com.meuprojetotcc.autenticacao_diplomas.model.notificacao.NotificacaoRequest;
import com.meuprojetotcc.autenticacao_diplomas.model.user.Role;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.repository.EstudanteRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.NotificacaoRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final UserRepository userRepository;
    private final EstudanteRepository estudanteRepository;

    public NotificacaoService(NotificacaoRepository notificacaoRepository,
                              UserRepository userRepository,
                              EstudanteRepository estudanteRepository) {
        this.notificacaoRepository = notificacaoRepository;
        this.userRepository = userRepository;
        this.estudanteRepository = estudanteRepository;
    }

    // ====================================
    // Enviar notificação do ADMIN
    // ====================================
    public List<Notificacao> enviarNotificacaoAdmin(NotificacaoRequest request) {

        User remetente = userRepository.findById(request.getRemetenteId())
                .orElseThrow(() -> new RuntimeException("Remetente não encontrado"));

        if(remetente.getRole() != Role.ADMIN) {
            throw new RuntimeException("Somente Admin pode usar este endpoint");
        }

        List<Notificacao> notificacoes = new ArrayList<>();

        // ===== Estudantes =====
        List<Long> estudantesIds = request.getEstudantesIds();
        if(estudantesIds == null || estudantesIds.isEmpty()) {
            estudantesIds = estudanteRepository.findAll()
                    .stream().map(e -> e.getId()).collect(Collectors.toList());
        }
        for(Long estId : estudantesIds) {
            Estudante estudante = estudanteRepository.findById(estId)
                    .orElseThrow(() -> new RuntimeException("Estudante não encontrado: " + estId));
            notificacoes.add(criarNotificacao(remetente, estudante, request.getMensagem()));
        }

        // ===== Usuários =====
        List<Long> usuariosIds = request.getUsuariosIds();
        if(usuariosIds == null || usuariosIds.isEmpty()) {
            usuariosIds = userRepository.findAll()
                    .stream().map(u -> u.getId()).collect(Collectors.toList());
        }
        for(Long userId : usuariosIds) {
            User destinatario = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + userId));
            notificacoes.add(criarNotificacao(remetente, destinatario, request.getMensagem()));
        }

        return notificacaoRepository.saveAll(notificacoes);
    }

    // ====================================
    // Enviar notificação do EMISSOR
    // ====================================
    public List<Notificacao> enviarNotificacaoEmissor(NotificacaoRequest request) {

        User remetente = userRepository.findById(request.getRemetenteId())
                .orElseThrow(() -> new RuntimeException("Remetente não encontrado"));

        if(remetente.getRole() != Role.EMISSOR) {
            throw new RuntimeException("Somente Emissor pode usar este endpoint");
        }

        List<Notificacao> notificacoes = new ArrayList<>();

        List<Long> estudantesIds = request.getEstudantesIds();
        if(estudantesIds == null || estudantesIds.isEmpty()) {
            estudantesIds = estudanteRepository.findAll()
                    .stream().map(e -> e.getId()).collect(Collectors.toList());
        }
        for(Long estId : estudantesIds) {
            Estudante estudante = estudanteRepository.findById(estId)
                    .orElseThrow(() -> new RuntimeException("Estudante não encontrado: " + estId));
            notificacoes.add(criarNotificacao(remetente, estudante, request.getMensagem()));
        }

        return notificacaoRepository.saveAll(notificacoes);
    }

    // ====================================
    // Métodos auxiliares
    // ====================================
    private Notificacao criarNotificacao(User remetente, Estudante estudante, String mensagem) {
        Notificacao n = new Notificacao();
        n.setUsuarioRemetente(remetente);
        n.setEstudante(estudante);
        n.setMensagem(mensagem);
        n.setDataCriacao(LocalDateTime.now());
        n.setLida(false);
        return n;
    }

    private Notificacao criarNotificacao(User remetente, User destinatario, String mensagem) {
        Notificacao n = new Notificacao();
        n.setUsuarioRemetente(remetente);
        n.setDestinatarioUser(destinatario);
        n.setMensagem(mensagem);
        n.setDataCriacao(LocalDateTime.now());
        n.setLida(false);
        return n;
    }

    // Converter para DTO
    public NotificacaoDto toDto(Notificacao n) {
        NotificacaoDto dto = new NotificacaoDto();
        dto.setId(n.getId());
        dto.setMensagem(n.getMensagem());
        dto.setDataCriacao(n.getDataCriacao());
        dto.setLida(n.isLida());
        if(n.getUsuarioRemetente() != null) dto.setUsuarioRemetenteId(n.getUsuarioRemetente().getId());
        if(n.getDestinatarioUser() != null) dto.setDestinatarioUserId(n.getDestinatarioUser().getId());
        if(n.getEstudante() != null) dto.setEstudanteId(n.getEstudante().getId());
        return dto;
    }
}
