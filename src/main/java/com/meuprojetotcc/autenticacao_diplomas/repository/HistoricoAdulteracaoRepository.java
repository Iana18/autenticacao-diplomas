package com.meuprojetotcc.autenticacao_diplomas.repository;

import com.meuprojetotcc.autenticacao_diplomas.model.HistoricoAdulteracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HistoricoAdulteracaoRepository extends JpaRepository<HistoricoAdulteracao, Long> {
    List<HistoricoAdulteracao> findByDiplomaId(Long diplomaId);
}
