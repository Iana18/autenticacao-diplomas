package com.meuprojetotcc.autenticacao_diplomas.repository;

import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import com.meuprojetotcc.autenticacao_diplomas.model.validacao.Validacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidacaoRepository extends JpaRepository<Validacao, Long> {
    // Aqui você pode criar buscas se precisar depois
    // Buscar validação por número de diploma atravessando a relação com Diploma
    Optional<Validacao> findByDiploma_NumeroDiploma(String numeroDiploma);
}
