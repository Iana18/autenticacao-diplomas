package com.meuprojetotcc.autenticacao_diplomas.repository;

import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Certificado;
///
// import com.meuprojetotcc.autenticacao_diplomas.model.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificadoRepository extends JpaRepository<Certificado, Long> {

    // Busca por estudante
   List<Certificado> findByEstudante(Estudante estudante);

    List<Certificado> findByEstudanteId(Long estudanteId);
    // Buscar certificados por nome do estudante
    List<Certificado> findByEstudante_NomeCompletoContainingIgnoreCase(String nome);

    // Busca por status
    Optional<Certificado> findByStatus(String status);

    // Busca todos os certificados com status específico
    List<Certificado> findAllByStatus(String status);

    // Busca por hash da blockchain (por exemplo, para validação)
    Optional<Certificado> findByHashBlockchain(String hashBlockchain);


}
