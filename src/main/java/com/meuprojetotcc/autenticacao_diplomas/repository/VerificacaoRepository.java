package com.meuprojetotcc.autenticacao_diplomas.repository;

import com.meuprojetotcc.autenticacao_diplomas.model.verificacao.Verificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerificacaoRepository extends JpaRepository<Verificacao, Long> {
    List<Verificacao> findByCertificadoId(Long certificadoId);
    List<Verificacao> findByVerificadorId(Long verificadorId);
}