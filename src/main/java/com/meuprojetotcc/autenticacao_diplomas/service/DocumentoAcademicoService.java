package com.meuprojetotcc.autenticacao_diplomas.service;

import com.meuprojetotcc.autenticacao_diplomas.model.DocumentoAcademico;
import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Certificado;
import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import com.meuprojetotcc.autenticacao_diplomas.repository.CertificadoRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.DiplomaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentoAcademicoService {

    private final CertificadoRepository certificadoRepository;
    private final DiplomaRepository diplomaRepository;

    public DocumentoAcademicoService(CertificadoRepository certificadoRepository,
                                     DiplomaRepository diplomaRepository) {
        this.certificadoRepository = certificadoRepository;
        this.diplomaRepository = diplomaRepository;
    }

    // =================== Revogar ===================
    public DocumentoAcademico revogarDocumento(DocumentoAcademico doc) {
        try {
            doc.revogar();
            if (doc instanceof Certificado) return certificadoRepository.save((Certificado) doc);
            if (doc instanceof Diploma) return diplomaRepository.save((Diploma) doc);
            throw new RuntimeException("Tipo de documento desconhecido");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao revogar documento: " + e.getMessage(), e);
        }
    }

    // =================== Reemitir ===================
    public DocumentoAcademico reemitirDocumento(DocumentoAcademico doc) {
        try {
            doc.reemitir();
            if (doc instanceof Certificado) return certificadoRepository.save((Certificado) doc);
            if (doc instanceof Diploma) return diplomaRepository.save((Diploma) doc);
            throw new RuntimeException("Tipo de documento desconhecido");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao reemitir documento: " + e.getMessage(), e);
        }
    }

    // =================== Buscar por Hash ===================
    public Optional<DocumentoAcademico> buscarPorHash(String hash) {
        try {
            Optional<Certificado> cert = certificadoRepository.findByHashBlockchain(hash);
            if (cert.isPresent()) return Optional.of(cert.get());

            Optional<Diploma> dipl = diplomaRepository.findByHashBlockchain(hash);
            return dipl.map(d -> (DocumentoAcademico) d);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar documento por hash: " + e.getMessage(), e);
        }
    }

    // =================== Buscar por Estudante ===================
    public List<DocumentoAcademico> buscarPorEstudante(Long estudanteId) {
        try {
            List<DocumentoAcademico> documentos = new ArrayList<>();
            List<Certificado> certificados = certificadoRepository.findByEstudanteId(estudanteId);
            List<Diploma> diplomas = diplomaRepository.findByEstudanteId(estudanteId);
            documentos.addAll(certificados);
            documentos.addAll(diplomas);
            return documentos;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar documentos do estudante: " + e.getMessage(), e);
        }
    }

    // =================== Atualizar Blockchain ===================
    public DocumentoAcademico atualizarBlockchain(DocumentoAcademico doc, String novoHash, String enderecoTx) {
        try {
            doc.setHashBlockchain(novoHash);
            doc.setEnderecoTransacao(enderecoTx);
            if (doc instanceof Certificado) return certificadoRepository.save((Certificado) doc);
            if (doc instanceof Diploma) return diplomaRepository.save((Diploma) doc);
            throw new RuntimeException("Tipo de documento desconhecido");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar blockchain: " + e.getMessage(), e);
        }
    }

    // =================== Buscar por ID genérico ===================
    public Optional<DocumentoAcademico> buscarPorId(Long id, Class<?> tipo) {
        try {
            if (tipo.equals(Certificado.class)) return certificadoRepository.findById(id).map(c -> (DocumentoAcademico) c);
            if (tipo.equals(Diploma.class)) return diplomaRepository.findById(id).map(d -> (DocumentoAcademico) d);
            throw new RuntimeException("Tipo de documento desconhecido");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar documento por ID: " + e.getMessage(), e);
        }
    }

    public List<DocumentoAcademico> buscarPorNomeEstudante(String nome) {
        List<DocumentoAcademico> documentos = new ArrayList<>();

        List<Certificado> certificados = certificadoRepository.findByEstudante_NomeCompletoContainingIgnoreCase(nome);
        List<Diploma> diplomas = diplomaRepository.findByEstudante_NomeCompletoContainingIgnoreCase(nome);

        documentos.addAll(certificados);
        documentos.addAll(diplomas);

        return documentos;
    }

}
