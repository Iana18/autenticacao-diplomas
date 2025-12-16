package com.meuprojetotcc.autenticacao_diplomas.repository;

import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.model.notificacao.Notificacao;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    List<Notificacao> findByUsuarioId(Long usuarioId);
    List<Notificacao> findByUsuarioOrderByDataCriacaoDesc(User usuario);

    List<Notificacao> findByDestinatarioUser(User user);
    List<Notificacao> findByEstudanteId(Long estudanteId);

    // Buscar notificações de um usuário destinatário


    // Buscar notificações de um estudante
    List<Notificacao> findByEstudante(Estudante estudante);
}
