package com.meuprojetotcc.autenticacao_diplomas.service;

import com.meuprojetotcc.autenticacao_diplomas.model.HistoricoAdulteracao;
import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import com.meuprojetotcc.autenticacao_diplomas.model.diploma.DiplomaRequestDTO;
import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.model.Curso.Curso;
import com.meuprojetotcc.autenticacao_diplomas.model.Instituicao.Instituicao;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Status;
import com.meuprojetotcc.autenticacao_diplomas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;
import javax.imageio.ImageIO;
import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;

@Service
public class DiplomaService {

    private final DiplomaRepository diplomaRepository;
    private final EstudanteRepository estudanteRepository;
    private final CursoRepository cursoRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final UserRepository userRepository;
    private final BlockchainService blockchainService;

    @Autowired
    private HistoricoAdulteracaoRepository historicoRepository;






    public DiplomaService(DiplomaRepository diplomaRepository, BlockchainService blockchainService,
                          EstudanteRepository estudanteRepository,
                          CursoRepository cursoRepository,
                          InstituicaoRepository instituicaoRepository,
                          UserRepository userRepository) {
        this.diplomaRepository = diplomaRepository;
        this.estudanteRepository = estudanteRepository;
        this.cursoRepository = cursoRepository;
        this.instituicaoRepository = instituicaoRepository;
        this.userRepository = userRepository;
        this.blockchainService= blockchainService;
    }



    // =================== Método para gerar número único ===================
    private String gerarNumeroDiplomaUnico() {
        long timestamp = System.currentTimeMillis();
        int ano = LocalDate.now().getYear();
        return "DIPL-" + ano + "-" + timestamp;
    }


    // =================== Registrar Diploma na Blockchain ===================

    public Diploma registrarNaBlockchain(Long diplomaId) throws Exception {
        Diploma diploma = diplomaRepository.findById(diplomaId)
                .orElseThrow(() -> new RuntimeException("Diploma não encontrado"));

        if (diploma.getStatus() != Status.ATIVO)
            throw new RuntimeException("Somente diplomas aprovados podem ser registrados na blockchain");

        // Gera hash final para blockchain
        diploma.gerarHashBlockchain();

        // Chamada real ao smart contract
        String txHash = blockchainService.registrarDiploma(diploma);

        diploma.setEnderecoTransacao(txHash);
        return diplomaRepository.save(diploma);
    }

  // =================== Registrar Diploma na Blockchain ===================
  /*public Diploma registrarNaBlockchain(Long diplomaId) throws Exception {
      Diploma diploma = diplomaRepository.findById(diplomaId)
              .orElseThrow(() -> new RuntimeException("Diploma não encontrado"));

      if (diploma.getStatus() == Status.ATIVO) {
          throw new RuntimeException("Diploma já registrado na blockchain");
      }

      // Envia o diploma completo para o smart contract e obtém TX hash real
      String txHash = blockchainService.registrarDiploma(diploma);

      diploma.setEnderecoTransacao(txHash);
      diploma.setStatus(Status.ATIVO);

      return diplomaRepository.save(diploma);
  }
*/

    // =================== Criar Diploma ===================
    public Diploma criarDiploma(DiplomaRequestDTO dto,
                                MultipartFile carimbo,
                                String assinaturaBase64,
                                UserDetails userDetails) {
        try {
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
            diploma.setCriadoPor(criadoPor);
            diploma.setTipoDiploma(dto.getTipoDiploma());
            diploma.setNotaFinal(dto.getNotaFinal());
            diploma.setCargaHoraria(dto.getCargaHoraria());
            diploma.setRegistroMinisterio(dto.getRegistroMinisterio());
            diploma.setGrauAcademico(dto.getGrauAcademico());
            diploma.setDataConclusao(dto.getDataConclusao().atStartOfDay());
            diploma.setDataEmissao(LocalDateTime.now());
            diploma.setStatus(Status.PENDENTE);
            diploma.setNumeroDiploma(gerarNumeroDiplomaUnico());

            // Carimbo
            if (carimbo != null && !carimbo.isEmpty()) {
                diploma.setCarimboInstituicao(carimbo.getBytes());
            }

            // Assinatura Base64
            if (assinaturaBase64 != null && !assinaturaBase64.isEmpty()) {
                // Remove prefixo "data:image/png;base64,"
                String base64 = assinaturaBase64.contains(",") ? assinaturaBase64.split(",")[1] : assinaturaBase64;
                byte[] assinaturaBytes = Base64.getDecoder().decode(base64);
                diploma.setAssinaturaInstituicao(assinaturaBytes);
            }

            //diploma.gerarHashBlockchain();
            diploma.setEnderecoTransacao(null);

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
        //d.setRegistroMinisterio(dto.getRegistroMinisterio());

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
  /*  public Diploma aprovar(Long id) {
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
*/


    public Diploma aprovar(Long id) throws Exception {
        Diploma diploma = diplomaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diploma não encontrado"));

        if (diploma.getStatus() != Status.PENDENTE)
            throw new RuntimeException("Somente diplomas pendentes podem ser aprovados");

        diploma.setStatus(Status.ATIVO);
        diploma.setDataEmissao(LocalDateTime.now());

        // Gera hash do diploma
        diploma.gerarHashBlockchain();

        // Envia para blockchain e obtém a transação
        String txHash = blockchainService.registrarDiploma(diploma);
        diploma.setEnderecoTransacao(txHash);

        return diplomaRepository.save(diploma);
    }




    // Buscar diploma pelo número e hash blockchain
    public Diploma buscarPorNumeroEHash(String numeroDiploma, String hash) {
        return diplomaRepository.findByNumeroDiplomaAndHashBlockchain(numeroDiploma, hash)
                .orElseThrow(() -> new RuntimeException("Diploma não encontrado ou inválido"));
    }




    // Método para registrar alterações suspeitas
    private void registrarAlteracoesSeAtivo(Diploma existente, Diploma atualizado) {
        if (existente.getStatus() != Status.ATIVO) return;

        Map<String, String> antigos = Map.of(
                "notaFinal", String.valueOf(existente.getNotaFinal()),
                "tipoDiploma", existente.getTipoDiploma(),
                "cargaHoraria", String.valueOf(existente.getCargaHoraria()),
                "grauAcademico", existente.getGrauAcademico().toString(),
                "dataConclusao", existente.getDataConclusao() != null ? existente.getDataConclusao().toString() : null
        );

        Map<String, String> novos = Map.of(
                "notaFinal", String.valueOf(atualizado.getNotaFinal()),
                "tipoDiploma", atualizado.getTipoDiploma(),
                "cargaHoraria", String.valueOf(atualizado.getCargaHoraria()),
                "grauAcademico", atualizado.getGrauAcademico().toString(),
                "dataConclusao", atualizado.getDataConclusao() != null ? atualizado.getDataConclusao().toString() : null
        );

        for (String campo : antigos.keySet()) {
            if (!Objects.equals(antigos.get(campo), novos.get(campo))) {
                HistoricoAdulteracao h = new HistoricoAdulteracao();
                h.setDiplomaId(existente.getId());
                h.setCampo(campo);
                h.setValorAntigo(antigos.get(campo));
                h.setValorNovo(novos.get(campo));
                historicoRepository.save(h);
            }
        }
    }

    // Atualizar diploma e registrar adulterações
    public Diploma atualizarDiplomaComHistorico(Long id, Diploma atualizado) {
        Diploma existente = diplomaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diploma não encontrado"));

        registrarAlteracoesSeAtivo(existente, atualizado);

        existente.setNotaFinal(atualizado.getNotaFinal());
        existente.setTipoDiploma(atualizado.getTipoDiploma());
        existente.setCargaHoraria(atualizado.getCargaHoraria());
        existente.setGrauAcademico(atualizado.getGrauAcademico());
        existente.setDataConclusao(atualizado.getDataConclusao());

        return diplomaRepository.save(existente);
    }

    // Validar diploma por número
    public String validarDiploma(String numeroDiploma) {
        Diploma diploma = diplomaRepository.findByNumeroDiploma(numeroDiploma)
                .orElseThrow(() -> new RuntimeException("Diploma não encontrado"));

        List<HistoricoAdulteracao> adulteracoes = historicoRepository.findByDiplomaId(diploma.getId());

        if (adulteracoes.isEmpty()) {
            return "VALIDO";
        } else {
            StringBuilder sb = new StringBuilder("INVALIDO. Campos adulterados:\n");
            adulteracoes.forEach(h -> sb.append(h.getCampo())
                    .append(": de '")
                    .append(h.getValorAntigo())
                    .append("' para '")
                    .append(h.getValorNovo())
                    .append("'\n"));
            return sb.toString();
        }
    }


    public Diploma buscarPorNumero(String numeroDiploma) {
        return diplomaRepository.findByNumeroDiploma(numeroDiploma)
                .orElseThrow(() -> new RuntimeException("Diploma não encontrado"));
    }







}
