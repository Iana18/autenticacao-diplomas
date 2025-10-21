package com.meuprojetotcc.autenticacao_diplomas.controller;

import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import com.meuprojetotcc.autenticacao_diplomas.model.diploma.DiplomaRequestDTO;
import com.meuprojetotcc.autenticacao_diplomas.model.diploma.DiplomaResponseDTO;
import com.meuprojetotcc.autenticacao_diplomas.service.DiplomaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/diplomas")
@CrossOrigin(origins = "*")
public class DiplomaController {

    private final DiplomaService diplomaService;

    public DiplomaController(DiplomaService diplomaService) {
        this.diplomaService = diplomaService;
    }

    // =================== Criar Diploma ===================
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody DiplomaRequestDTO dto) {
        try {
            Diploma d = diplomaService.criarDiploma(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponseDTO(d));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno: " + e.getMessage());
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
        dto.setStatus(d.getStatus().toString());
        return dto;
    }
}
