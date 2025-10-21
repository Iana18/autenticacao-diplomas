package com.meuprojetotcc.autenticacao_diplomas.service;

import com.meuprojetotcc.autenticacao_diplomas.model.Curso.Curso;
import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.model.Instituicao.Instituicao;
import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Certificado;
import com.meuprojetotcc.autenticacao_diplomas.model.certificado.CertificadoRequestDTO;
import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Status;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    // =================== Registrar ===================
    public Certificado registrarCertificado(CertificadoRequestDTO dto) {
        Estudante estudante = estudanteRepository.findById(dto.getEstudanteId())
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado"));
        Curso curso = cursoRepository.findById(dto.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));
        Instituicao instituicao = instituicaoRepository.findById(dto.getInstituicaoId())
                .orElseThrow(() -> new RuntimeException("Instituição não encontrada"));
        User criadoPor = userRepository.findById(dto.getCriadoPorId())
                .orElseThrow(() -> new RuntimeException("Usuário criador não encontrado"));

        Certificado c = new Certificado();
        c.setEstudante(estudante);
        c.setCurso(curso);
        c.setInstituicao(instituicao);
        c.setCriadoPor(criadoPor);
        c.setDataEmissao(LocalDateTime.now());
        c.setStatus(Status.ATIVO);

        c.setTipoParticipacao(dto.getTipoParticipacao());
        c.setCargaHoraria(dto.getCargaHoraria());

        // Gera hash automaticamente
        c.gerarHashBlockchain();

        // Simula envio para blockchain
        c.setEnderecoTransacao(enviarParaBlockchain(c.getHashBlockchain()));

        return certificadoRepository.save(c);
    }

    // =================== Listar por estudante ===================
    public List<Certificado> verPorEstudante(Long estudanteId) {
        Estudante estudante = estudanteRepository.findById(estudanteId)
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado"));
        return certificadoRepository.findByEstudante(estudante);
    }

    // =================== Listar todos ===================
    public List<Certificado> verTodos() {
        return certificadoRepository.findAll();
    }

    // =================== Revogar ===================
    public Certificado revogar(Long id) {
        Certificado c = certificadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificado não encontrado"));
        c.setStatus(Status.REVOGADO);
        c.setDataRevogacao(LocalDateTime.now());
        return certificadoRepository.save(c);
    }

    // =================== Reemitir ===================
    public Certificado reemitir(Long id) {
        Certificado c = certificadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificado não encontrado"));
        c.setStatus(Status.ATIVO);
        c.setDataRevogacao(null);
        c.setDataEmissao(LocalDateTime.now());
        return certificadoRepository.save(c);
    }

    // =================== Atualizar ===================
    public Certificado atualizar(Long id, CertificadoRequestDTO dto) {
        Certificado c = certificadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificado não encontrado"));

        // Só permite atualizar campos seguros
        if (dto.getTipoParticipacao() != null) c.setTipoParticipacao(dto.getTipoParticipacao());
        if (dto.getCargaHoraria() > 0) c.setCargaHoraria(dto.getCargaHoraria());

        return certificadoRepository.save(c);
    }

    // =================== Método simulado para blockchain ===================
    private String enviarParaBlockchain(String hash) {
        // Aqui você chamaria a API blockchain real
        return "tx_" + hash.substring(0, 10); // Simula TxID
    }
}
