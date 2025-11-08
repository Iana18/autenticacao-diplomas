package com.meuprojetotcc.autenticacao_diplomas.repository;

import com.meuprojetotcc.autenticacao_diplomas.model.Instituicao.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {

    Optional<Instituicao> findByNome(String nome);

    List<Instituicao> findByNomeContainingIgnoreCase(String nome);

    boolean existsByNome(String nome);
    Optional<Instituicao> findByNomeIgnoreCase(String nome);
}
