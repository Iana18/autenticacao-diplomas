package com.meuprojetotcc.autenticacao_diplomas.service;

import com.meuprojetotcc.autenticacao_diplomas.model.notificacao.Notificacao;

import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.repository.NotificacaoRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final UserRepository userRepository;

    public NotificacaoService(NotificacaoRepository repo, UserRepository userRepo) {
        this.notificacaoRepository = repo;
        this.userRepository = userRepo;
    }

    public Notificacao criar(Long usuarioId, String mensagem) {
        Optional<User> usuario = userRepository.findById(usuarioId);
        if (usuario.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }
        Notificacao n = new Notificacao();
        n.setUsuario(usuario.get());
        n.setMensagem(mensagem);
        n.setDataCriacao(LocalDateTime.now());
        n.setLida(false);
        return notificacaoRepository.save(n);
    }

    public List<Notificacao> listarPorUsuario(Long usuarioId) {
        return notificacaoRepository.findByUsuarioId(usuarioId);
    }

    public void marcarComoLida(Long id) {
        Optional<Notificacao> n = notificacaoRepository.findById(id);
        if (n.isPresent()) {
            Notificacao notif = n.get();
            notif.setLida(true);
            notificacaoRepository.save(notif);
        }
    }
}
