package com.meuprojetotcc.autenticacao_diplomas.service;
import com.meuprojetotcc.autenticacao_diplomas.model.certificado.CertificadoDTO;
import com.meuprojetotcc.autenticacao_diplomas.model.Curso.Curso;
import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.model.Instituicao.Instituicao;
import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Certificado;
import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Status;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CertificadoService {
    private final CertificadoRepository certificadoRepository;
    private final EstudanteRepository estudanteRepository;
    private final CursoRepository cursoRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final UserRepository userRepository;

    public CertificadoService(CertificadoRepository certificadoRepository,
                              EstudanteRepository estudanteRepository,
                              CursoRepository cursoRepository,
                              InstituicaoRepository instituicaoRepository,
                              UserRepository userRepository) {
        this.certificadoRepository = certificadoRepository;
        this.estudanteRepository = estudanteRepository;
        this.cursoRepository = cursoRepository;
        this.instituicaoRepository = instituicaoRepository;
        this.userRepository = userRepository;
    }

    public Certificado registrarCertificado(CertificadoDTO dto) {
        Optional<Estudante> estudante = estudanteRepository.findById(dto.getEstudanteId());
        Optional<Curso> curso = cursoRepository.findById(dto.getCursoId());
        Optional<Instituicao> instituicao = instituicaoRepository.findById(dto.getInstituicaoId());
        Optional<User> criadoPor = userRepository.findById(dto.getCriadoPorId());

        if (estudante.isEmpty() || curso.isEmpty() || instituicao.isEmpty() || criadoPor.isEmpty()) {
            throw new RuntimeException("Dados inválidos para emitir o certificado.");
        }

        Certificado c = new Certificado();
        c.setEstudante(estudante.get());
        c.setCurso(curso.get());
        c.setInstituicao(instituicao.get());
        c.setCriadoPor(criadoPor.get());
        c.setDataEmissao(LocalDateTime.now());
        c.setStatus(Status.ATIVO);
        c.setDataRevogacao(null);
        c.setEnderecoTransacao(dto.getEnderecoTransacao());
        c.setHashBlockchain(dto.getHashBlockchain());

        return certificadoRepository.save(c);
    }

    public List<Certificado> verPorEstudante(Long estudanteId) {
        Optional<Estudante> estudante = estudanteRepository.findById(estudanteId);
        return estudante.map(certificadoRepository::findByEstudante).orElse(null);
    }

    public List<Certificado> verTodos() {
        return certificadoRepository.findAll();
    }

    public Certificado revogar(Long id) {
        Optional<Certificado> opt = certificadoRepository.findById(id);
        if (opt.isPresent()) {
            Certificado c = opt.get();
            c.setStatus(Status.REVOGADO);
            c.setDataRevogacao(LocalDateTime.now());
            return certificadoRepository.save(c);
        }
        throw new RuntimeException("Certificado não encontrado");
    }

    public Certificado reemitir(Long id) {
        Optional<Certificado> opt = certificadoRepository.findById(id);
        if (opt.isPresent()) {
            Certificado c = opt.get();
            c.setStatus(Status.ATIVO);
            c.setDataRevogacao(null);
            c.setDataEmissao(LocalDateTime.now());
            return certificadoRepository.save(c);
        }
        throw new RuntimeException("Certificado não encontrado");
    }

    public Certificado atualizar(Long id, CertificadoDTO dto) {
        Certificado certificado = certificadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificado não encontrado"));

        if (dto.getEnderecoTransacao() != null) {
            certificado.setEnderecoTransacao(dto.getEnderecoTransacao());
        }

        if (dto.getHashBlockchain() != null) {
            certificado.setHashBlockchain(dto.getHashBlockchain());
        }

        if (dto.getStatus() != null) {
            certificado.setStatus(dto.getStatus());
            if (dto.getStatus() == Status.REVOGADO) {
                certificado.setDataRevogacao(LocalDateTime.now());
            } else if (dto.getStatus() == Status.ATIVO) {
                certificado.setDataRevogacao(null);
            }
        }

        // Atualizar outras propriedades se necessário

        return certificadoRepository.save(certificado);
    }
}