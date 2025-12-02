package com.meuprojetotcc.autenticacao_diplomas.repository;

import com.meuprojetotcc.autenticacao_diplomas.model.validacao.Validacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidacaoRepository extends JpaRepository<Validacao, Long> {
    // Aqui você pode criar buscas se precisar depois
}
