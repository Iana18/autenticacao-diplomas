package com.meuprojetotcc.autenticacao_diplomas.repository;

import com.meuprojetotcc.autenticacao_diplomas.model.verificacao.Verificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerificacaoRepository extends JpaRepository<Verificacao, Long> {

    // Buscar por diploma
    List<Verificacao> findByDiploma_Id(Long diplomaId);

    // Buscar por verificador
    List<Verificacao> findByVerificador_Id(Long verificadorId);
}