package com.meuprojetotcc.autenticacao_diplomas.controller;

import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Status;
import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import com.meuprojetotcc.autenticacao_diplomas.model.diploma.DiplomaRequestDTO;
import com.meuprojetotcc.autenticacao_diplomas.model.diploma.DiplomaResponseDTO;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.repository.DiplomaRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.UserRepository;
import com.meuprojetotcc.autenticacao_diplomas.seguranca.JwtUtil;
import com.meuprojetotcc.autenticacao_diplomas.service.BlockchainService;
import com.meuprojetotcc.autenticacao_diplomas.service.CarimboDigitalService;
import com.meuprojetotcc.autenticacao_diplomas.service.DiplomaPdfService;
import com.meuprojetotcc.autenticacao_diplomas.service.DiplomaService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static java.util.Map.entry;
import java.util.Map;

@RestController
@RequestMapping("/api/diplomas")
@CrossOrigin(origins = "*")
public class DiplomaController {

    private final DiplomaService diplomaService;
    private final DiplomaPdfService pdfService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final BlockchainService blockchainService;
    private final DiplomaRepository diplomaRepository;
    private  final CarimboDigitalService carimboDigitalService;
    public DiplomaController(DiplomaService diplomaService, DiplomaPdfService pdfService,JwtUtil jwtUtil, UserRepository userRepository,
                             BlockchainService blockchainService,DiplomaRepository diplomaRepository,CarimboDigitalService carimboDigitalService) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.diplomaService = diplomaService;
        this.pdfService = pdfService;
        this.blockchainService= blockchainService;
        this.diplomaRepository =diplomaRepository;
        this.carimboDigitalService = carimboDigitalService;
    }

    // =================== Criar Diploma ===================

    // =================== Criar Diploma com arquivos ===================
    @PostMapping(value = "/com-arquivos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> criarComArquivos(
            @RequestPart("diploma") DiplomaRequestDTO dto,
            @RequestPart(value = "carimbo", required = false) MultipartFile carimbo,
            @RequestPart(value = "assinaturaDesenhada", required = false) String assinaturaBase64,
            @RequestHeader("Authorization") String tokenHeader) {

        try {
            String token = tokenHeader.replace("Bearer ", "");
            String username = jwtUtil.extractUsername(token);

            // Busca o User completo no banco
            User usuario = userRepository.findByEmail(username)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado no token"));

            // Cria diploma
            Diploma diploma = diplomaService.criarDiploma(dto, carimbo, assinaturaBase64, usuario);

            // Gera e salva transação na blockchain
            //String txHash = blockchainService.registrarDiploma(diploma);
            // diploma.setEnderecoTransacao(txHash);

            diplomaRepository.save(diploma);

            return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponseDTO(diploma));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro interno: " + e.getMessage()));
        }
    }




    // =================== Listar todos ===================
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        try {
            List<Diploma> lista = diplomaService.listarTodos();
            List<DiplomaResponseDTO> responses = lista.stream()
                    .map(this::mapToResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao listar diplomas: " + e.getMessage());
        }
    }



    // =================== Buscar por estudante ===================
    @GetMapping("/estudante/{id}")
    public ResponseEntity<?> buscarPorEstudante(@PathVariable Long id) {
        try {
            List<Diploma> lista = diplomaService.buscarPorEstudante(id);
            if (lista.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Nenhum diploma encontrado para o estudante ID " + id);
            }
            List<DiplomaResponseDTO> responses = lista.stream()
                    .map(this::mapToResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno: " + e.getMessage());
        }
    }

    // =================== Buscar por ID ===================
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Diploma d = diplomaService.buscarPorId(id)
                    .orElseThrow(() -> new RuntimeException("Diploma não encontrado"));
            return ResponseEntity.ok(mapToResponseDTO(d));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno: " + e.getMessage());
        }
    }

    // =================== Revogar ===================
    @PutMapping("/{id}/revogar")
    public ResponseEntity<?> revogar(@PathVariable Long id) {
        try {
            Diploma d = diplomaService.revogar(id);
            return ResponseEntity.ok(mapToResponseDTO(d));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao revogar diploma: " + e.getMessage());
        }
    }

    // =================== Reemitir ===================
    @PutMapping("/{id}/reemitir")
    public ResponseEntity<?> reemitir(@PathVariable Long id) {
        try {
            Diploma d = diplomaService.reemitir(id);
            return ResponseEntity.ok(mapToResponseDTO(d));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao reemitir diploma: " + e.getMessage());
        }
    }

    // =================== Atualizar Blockchain ===================
    @PutMapping("/{id}/blockchain")
    public ResponseEntity<?> atualizarBlockchain(@PathVariable Long id,
                                                 @RequestParam String hash,
                                                 @RequestParam String enderecoTx) {
        try {
            Diploma d = diplomaService.atualizarBlockchain(id, hash, enderecoTx);
            return ResponseEntity.ok(mapToResponseDTO(d));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar blockchain: " + e.getMessage());
        }
    }

    // =================== Atualizar Dados ===================
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody DiplomaRequestDTO dto) {
        try {
            Diploma d = diplomaService.atualizarDiploma(id, dto);
            return ResponseEntity.ok(mapToResponseDTO(d));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar diploma: " + e.getMessage());
        }
    }

    // =================== Verificar Diploma ===================
    @GetMapping("/verificar")
    public ResponseEntity<Diploma> verificarDiploma(
            @RequestParam String hash,
            @RequestParam String numeroMatricula) {

        return diplomaService.buscarPorHashEEstudante(hash, numeroMatricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // =================== Download PDF ===================
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> baixarPdf(@PathVariable Long id) throws Exception {
        Diploma diploma = diplomaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Diploma não encontrado"));

        byte[] pdf = pdfService.gerarPdf(diploma);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=diploma.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    // =================== Buscar por número de matrícula ===================
    @GetMapping("/por-estudante/{matricula}")
    public ResponseEntity<?> buscarPorNumeroMatricula(@PathVariable String matricula) {
        try {
            List<Diploma> lista = diplomaService.buscarPorNumeroMatricula(matricula);

            if (lista.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Nenhum diploma encontrado para a matrícula: " + matricula);
            }

            List<DiplomaResponseDTO> responses = lista.stream()
                    .map(this::mapToResponseDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(responses);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar diploma: " + e.getMessage());
        }
    }

    @GetMapping("/meus-diplomas")
    public ResponseEntity<?> listarDiplomasDoEstudante(@RequestHeader("Authorization") String tokenHeader) {
        try {
            String token = tokenHeader.replace("Bearer ", "");
            String numeroMatricula = jwtUtil.extractClaim(token, claims -> claims.get("numeroMatricula", String.class));

            if (numeroMatricula == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Número de matrícula não encontrado no token");
            }

            List<Diploma> diplomas = diplomaService.buscarPorNumeroMatricula(numeroMatricula)
                    .stream()
                    .filter(d -> d.getStatus() == Status.ATIVO)
                    .toList();

            if (diplomas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Nenhum diploma aprovado encontrado para o estudante");
            }

            List<DiplomaResponseDTO> responses = diplomas.stream()
                    .map(this::mapToResponseDTO)
                    .toList();

            return ResponseEntity.ok(responses);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar diplomas: " + e.getMessage());
        }
    }

    // =================== Aprovar Diploma (Emitir oficialmente) ===================
    @PutMapping("/{id}/aprovar")
    public ResponseEntity<?> aprovarDiploma(@PathVariable Long id) {
        try {
            Diploma diploma = diplomaService.aprovar(id);
            return ResponseEntity.ok(mapToResponseDTO(diploma));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao aprovar e registrar diploma na blockchain: " + e.getMessage());
        }
    }




    @PostMapping("/registrar-blockchain/{id}")
    public ResponseEntity<?> registrarDiploma(@PathVariable Long id) {
        try {
            Diploma diploma = diplomaService.registrarNaBlockchain(id);
            return ResponseEntity.ok(Map.of(
                    "hashBlockchain", diploma.getHashBlockchain(),
                    "txHash", diploma.getEnderecoTransacao()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao registrar na blockchain: " + e.getMessage());
        }
    }



    // =================== Mapper para ResponseDTO ===================
    private DiplomaResponseDTO mapToResponseDTO(Diploma d) {
        DiplomaResponseDTO dto = new DiplomaResponseDTO();
        dto.setId(d.getId());
        dto.setEstudanteNome(d.getEstudante().getNomeCompleto());
        dto.setCursoNome(d.getCurso().getNome());
        dto.setInstituicaoNome(d.getInstituicao().getNome());
        dto.setCriadoPorNome(d.getCriadoPor().getNome());
        dto.setTipoDiploma(d.getTipoDiploma());
        dto.setNotaFinal(d.getNotaFinal());
        dto.setCargaHoraria(d.getCargaHoraria());
        dto.setNumeroDiploma(d.getNumeroDiploma());
        dto.setRegistroMinisterio(d.getRegistroMinisterio());
        dto.setGrauAcademico(d.getGrauAcademico().toString());
        dto.setDataConclusao(d.getDataConclusao().toLocalDate());
        dto.setDataEmissao(d.getDataEmissao());
        dto.setDataRevogacao(d.getDataRevogacao());
        dto.setHashBlockchain(d.getHashBlockchain());
        dto.setEnderecoTransacao(d.getEnderecoTransacao());
        //dto.getEnderecoTransacao(); // <-- aqui vai o hash real
        dto.setStatus(d.getStatus().toString());

        // ✅ Adicionando assinatura e carimbo exatamente como estão no banco (String Base64)
        dto.setAssinaturaInstituicao(d.getAssinaturaInstituicao());
        dto.setCarimboInstituicao(d.getCarimboInstituicao());

        return dto;
    }


    @GetMapping("/validar/{numeroDiploma}")
    public ResponseEntity<?> validar(@PathVariable String numeroDiploma) {
        try {
            // Verifica se o diploma é válido ou adulterado
            String resultadoValidacao = diplomaService.validarDiploma(numeroDiploma);

            Diploma diploma = diplomaService.buscarPorNumero(numeroDiploma);

            if (resultadoValidacao.equals("VALIDO")) {
                // Importante: precisa do import static java.util.Map.entry;
                return ResponseEntity.ok(Map.ofEntries(
                        entry("status", "Válido ✅"),
                        entry("mensagem", "Diploma verificado com sucesso."),
                        entry("dados", Map.ofEntries(
                                entry("nomeEstudante", diploma.getEstudante().getNomeCompleto()),
                                entry("numeroDiploma", diploma.getNumeroDiploma()),
                                entry("cursoNome", diploma.getCurso().getNome()),
                                entry("instituicaoNome", diploma.getInstituicao().getNome()),
                                entry("grauAcademico", diploma.getGrauAcademico().toString()),
                                entry("notaFinal", diploma.getNotaFinal()),
                                entry("cargaHoraria", diploma.getCargaHoraria()),
                                entry("assinaturaInstituicao", diploma.getAssinaturaInstituicao()),
                                entry("carimboInstituicao", diploma.getCarimboInstituicao()),
                                entry("dataConclusao", diploma.getDataConclusao()),
                                entry("dataEmissao", diploma.getDataEmissao()),
                                entry("hashBlockchain", diploma.getHashBlockchain()),
                                entry("enderecoTransacao", diploma.getEnderecoTransacao())
                        ))
                ));
            } else {
                // Retorna inválido com detalhes
                return ResponseEntity.status(404).body(Map.of(
                        "status", "Inválido ❌",
                        "mensagem", resultadoValidacao
                ));
            }

        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of(
                    "status", "Inválido ❌",
                    "mensagem", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "status", "Erro ❌",
                    "mensagem", "Erro interno do servidor."
            ));
        }
    }



}
