package com.meuprojetotcc.autenticacao_diplomas.repository;

import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstudanteRepository extends JpaRepository<Estudante, Long> {

    Optional<Estudante> findByEmail(String email);

    Optional<Estudante> findByNumeroMatricula(String numeroMatricula);

    List<Estudante> findByNomeCompleto(String nomeCompleto);

    List<Estudante> findByNomeCompletoContainingIgnoreCase(String nomeCompleto);

    boolean existsByEmail(String email);
    Optional<Estudante> findByNomeCompletoAndNumeroMatricula(String nomeCompleto, String numeroMatricula);


    boolean existsByNumeroMatricula(String numeroMatricula);




}
