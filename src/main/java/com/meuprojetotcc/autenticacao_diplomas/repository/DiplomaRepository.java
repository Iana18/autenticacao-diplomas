package com.meuprojetotcc.autenticacao_diplomas.repository;

import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Certificado;
import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiplomaRepository extends JpaRepository<Diploma, Long> {

    List<Diploma> findByEstudanteId(Long estudanteId);
    List<Diploma> findByEstudante(Estudante estudante);
    Optional<Diploma> findByHashBlockchain(String hashBlockchain);
    // Buscar diplomas por nome do estudante
    List<Diploma> findByEstudante_NomeCompletoContainingIgnoreCase(String nome);

    List<Diploma> findByEstudante_NumeroMatricula(String numeroMatricula);
    // Retorna todos os diplomas de um estudante específico
    Optional<Diploma> findByHashBlockchainAndEstudante_NumeroMatricula(String hashBlockchain, String numeroMatricula);



    // Verifica se existe pelo menos um diploma vinculado a um estudante com esse número de matrícula
    boolean existsByEstudante_NumeroMatricula(String numeroMatricula);

    // Se quiser pegar um diploma específico


}
