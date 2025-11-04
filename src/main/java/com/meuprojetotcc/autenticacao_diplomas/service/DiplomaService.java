package com.meuprojetotcc.autenticacao_diplomas.service;

import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import com.meuprojetotcc.autenticacao_diplomas.model.diploma.DiplomaRequestDTO;
import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.model.Curso.Curso;
import com.meuprojetotcc.autenticacao_diplomas.model.Instituicao.Instituicao;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Status;
import com.meuprojetotcc.autenticacao_diplomas.repository.DiplomaRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.EstudanteRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.CursoRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.InstituicaoRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

@Service
public class DiplomaService {

    private final DiplomaRepository diplomaRepository;
    private final EstudanteRepository estudanteRepository;
    private final CursoRepository cursoRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final UserRepository userRepository;

    public DiplomaService(DiplomaRepository diplomaRepository,
                          EstudanteRepository estudanteRepository,
                          CursoRepository cursoRepository,
                          InstituicaoRepository instituicaoRepository,
                          UserRepository userRepository) {
        this.diplomaRepository = diplomaRepository;
        this.estudanteRepository = estudanteRepository;
        this.cursoRepository = cursoRepository;
        this.instituicaoRepository = instituicaoRepository;
        this.userRepository = userRepository;
    }



    // =================== Método para gerar número único ===================
    private String gerarNumeroDiplomaUnico() {
        long timestamp = System.currentTimeMillis();
        int ano = LocalDate.now().getYear();
        return "DIPL-" + ano + "-" + timestamp;
    }


    // =================== Criar Diploma ===================
    public Diploma criarDiploma(DiplomaRequestDTO dto,
                                MultipartFile carimbo,
                                MultipartFile assinatura,
                                UserDetails userDetails) {
        try {
            // Usuário logado
            User criadoPor = userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            Estudante estudante = dto.getEstudante();
            Curso curso = dto.getCurso();
            Instituicao instituicao = dto.getInstituicao();

            if (estudante == null || curso == null || instituicao == null) {
                throw new RuntimeException("Estudante, curso ou instituição não podem ser nulos");
            }

            Diploma diploma = new Diploma();
            diploma.setEstudante(estudante);
            diploma.setCurso(curso);
            diploma.setInstituicao(instituicao);
            diploma.setCriadoPor(criadoPor); // agora vem do token
            diploma.setTipoDiploma(dto.getTipoDiploma());
            diploma.setNotaFinal(dto.getNotaFinal());
            diploma.setCargaHoraria(dto.getCargaHoraria());
            diploma.setRegistroMinisterio(dto.getRegistroMinisterio());
            diploma.setGrauAcademico(dto.getGrauAcademico());
            diploma.setDataConclusao(dto.getDataConclusao().atStartOfDay());
            diploma.setDataEmissao(LocalDateTime.now());
            diploma.setStatus(Status.PENDENTE);

            // Número de diploma único
            diploma.setNumeroDiploma(gerarNumeroDiplomaUnico());

            // Carimbo e assinatura
            if (carimbo != null && !carimbo.isEmpty()) {
                diploma.setCarimboInstituicao(Base64.getEncoder().encodeToString(carimbo.getBytes()));
            }
            if (assinatura != null && !assinatura.isEmpty()) {
                diploma.setAssinaturaInstituicao(Base64.getEncoder().encodeToString(assinatura.getBytes()));
            }

            diploma.gerarHashBlockchain();
            diploma.setEnderecoTransacao("tx_" + System.currentTimeMillis());

            return diplomaRepository.save(diploma);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar diploma: " + e.getMessage(), e);
        }
    }


    // =================== Listar Todos ===================
    public List<Diploma> listarTodos() {
        return diplomaRepository.findAll();
    }

    // =================== Buscar por Estudante ===================
    public List<Diploma> buscarPorEstudante(Long estudanteId) {
        return diplomaRepository.findByEstudanteId(estudanteId);
    }

    // =================== Buscar por ID ===================
    public Optional<Diploma> buscarPorId(Long id) {
        return diplomaRepository.findById(id);
    }

    // =================== Revogar Diploma ===================
    public Diploma revogar(Long id) {
        Diploma d = diplomaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diploma não encontrado"));
        d.revogar();
        return diplomaRepository.save(d);
    }

    // =================== Reemitir Diploma ===================
    public Diploma reemitir(Long id) {
        Diploma d = diplomaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diploma não encontrado"));
        d.reemitir();
        return diplomaRepository.save(d);
    }

    // =================== Atualizar Blockchain ===================
    public Diploma atualizarBlockchain(Long id, String novoHash, String enderecoTx) {
        Diploma d = diplomaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diploma não encontrado"));
        d.setHashBlockchain(novoHash);
        d.setEnderecoTransacao(enderecoTx);
        return diplomaRepository.save(d);
    }

    // =================== Atualizar dados do Diploma ===================
    public Diploma atualizarDiploma(Long id, DiplomaRequestDTO dto) {
        Diploma d = diplomaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diploma não encontrado"));

        d.setTipoDiploma(dto.getTipoDiploma());
        d.setNotaFinal(dto.getNotaFinal());
        d.setCargaHoraria(dto.getCargaHoraria());
        d.setNumeroDiploma(dto.getNumeroDiploma());
        d.setRegistroMinisterio(dto.getRegistroMinisterio());

        // Já é enum, pode setar direto
        d.setGrauAcademico(dto.getGrauAcademico());

        // Converte LocalDate para LocalDateTime no início do dia
        d.setDataConclusao(dto.getDataConclusao().atStartOfDay());

        // Atualiza hash blockchain e transação caso queira reemitir
        d.gerarHashBlockchain();
        d.setEnderecoTransacao("tx_" + System.currentTimeMillis());

        return diplomaRepository.save(d);
    }

    public List<Diploma> buscarPorNumeroMatricula(String numeroMatricula) {
        return diplomaRepository.findByEstudante_NumeroMatricula(numeroMatricula);
    }

    // =================== Buscar por hash E número de matrícula ===================
    public Optional<Diploma> buscarPorHashEEstudante(String hashBlockchain, String numeroMatricula) {
        return diplomaRepository.findByHashBlockchainAndEstudante_NumeroMatricula(hashBlockchain, numeroMatricula);
    }

    // =================== Aprovar Diploma ===================
    public Diploma aprovar(Long id) {
        Diploma diploma = diplomaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diploma não encontrado"));

        if (diploma.getStatus() != Status.PENDENTE) {
            throw new RuntimeException("Somente diplomas pendentes podem ser aprovados");
        }

        diploma.setStatus(Status.ATIVO);
        diploma.setDataEmissao(LocalDateTime.now());
        diploma.gerarHashBlockchain();
        diploma.setEnderecoTransacao("tx_" + System.currentTimeMillis());

        return diplomaRepository.save(diploma);
    }

}
