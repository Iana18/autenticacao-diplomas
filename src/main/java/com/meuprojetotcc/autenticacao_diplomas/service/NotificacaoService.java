package com.meuprojetotcc.autenticacao_diplomas.service;

import com.meuprojetotcc.autenticacao_diplomas.model.notificacao.Notificacao;
import com.meuprojetotcc.autenticacao_diplomas.repository.NotificacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;

    public NotificacaoService(NotificacaoRepository notificacaoRepository) {
        this.notificacaoRepository = notificacaoRepository;
    }

    public List<Notificacao> listarPorUsuario(Long usuarioId) {
        return notificacaoRepository.findByUsuarioId(usuarioId);
    }

    public Notificacao criarNotificacao(Notificacao notificacao) {
        notificacao.setDataCriacao(LocalDateTime.now());
        notificacao.setLida(false);
        return notificacaoRepository.save(notificacao);
    }

    public Notificacao atualizarNotificacao(Long notificacaoId, String novaMensagem, Boolean lida) {
        Optional<Notificacao> notifOpt = notificacaoRepository.findById(notificacaoId);
        if (notifOpt.isEmpty()) {
            throw new RuntimeException("Notificação não encontrada.");
        }

        Notificacao notif = notifOpt.get();

        if (novaMensagem != null) {
            notif.setMensagem(novaMensagem);
        }
        if (lida != null) {
            notif.setLida(lida);
        }

        return notificacaoRepository.save(notif);
    }

    public void deletarNotificacao(Long notificacaoId) {
        if (!notificacaoRepository.existsById(notificacaoId)) {
            throw new RuntimeException("Notificação não encontrada.");
        }
        notificacaoRepository.deleteById(notificacaoId);
    }
}
