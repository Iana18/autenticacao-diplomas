package com.meuprojetotcc.autenticacao_diplomas.controller;

import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Certificado;
import com.meuprojetotcc.autenticacao_diplomas.model.certificado.CertificadoRequestDTO;
import com.meuprojetotcc.autenticacao_diplomas.model.certificado.CertificadoResponseDTO;
import com.meuprojetotcc.autenticacao_diplomas.service.CertificadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/certificados")
@CrossOrigin(origins = "*")
public class CertificadoController {

    private final CertificadoService certificadoService;

    public CertificadoController(CertificadoService certificadoService) {
        this.certificadoService = certificadoService;
    }

    // =================== Criar ===================
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody CertificadoRequestDTO dto) {
        try {
            Certificado certificado = certificadoService.registrarCertificado(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponseDTO(certificado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }
    }

    // =================== Listar todos ===================
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        try {
            List<Certificado> lista = certificadoService.verTodos();
            List<CertificadoResponseDTO> responses = lista.stream()
                    .map(this::mapToResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar certificados: " + e.getMessage());
        }
    }

    // =================== Buscar por estudante ===================
    @GetMapping("/estudante/{id}")
    public ResponseEntity<?> listarPorEstudante(@PathVariable Long id) {
        try {
            List<Certificado> lista = certificadoService.verPorEstudante(id);
            if (lista == null || lista.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Nenhum certificado encontrado para o estudante ID " + id);
            }
            List<CertificadoResponseDTO> responses = lista.stream()
                    .map(this::mapToResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }
    }

    // =================== Revogar ===================
    @PutMapping("/{id}/revogar")
    public ResponseEntity<?> revogar(@PathVariable Long id) {
        try {
            Certificado c = certificadoService.revogar(id);
            return ResponseEntity.ok(mapToResponseDTO(c));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao revogar certificado: " + e.getMessage());
        }
    }

    // =================== Reemitir ===================
    @PutMapping("/{id}/reemitir")
    public ResponseEntity<?> reemitir(@PathVariable Long id) {
        try {
            Certificado c = certificadoService.reemitir(id);
            return ResponseEntity.ok(mapToResponseDTO(c));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao reemitir certificado: " + e.getMessage());
        }
    }

    // =================== Atualizar ===================
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody CertificadoRequestDTO dto) {
        try {
            Certificado c = certificadoService.atualizar(id, dto);
            return ResponseEntity.ok(mapToResponseDTO(c));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar certificado: " + e.getMessage());
        }
    }

    // =================== Mapper ===================
    private CertificadoResponseDTO mapToResponseDTO(Certificado c) {
        CertificadoResponseDTO dto = new CertificadoResponseDTO();
        dto.setId(c.getId());
        dto.setEstudanteNome(c.getEstudante().getNomeCompleto());
        dto.setCursoNome(c.getCurso().getNome());
        dto.setInstituicaoNome(c.getInstituicao().getNome());
        dto.setCriadoPorNome(c.getCriadoPor().getNome());
        dto.setTipoParticipacao(c.getTipoParticipacao());
        dto.setCargaHoraria(c.getCargaHoraria());
        dto.setDataEmissao(c.getDataEmissao());
        dto.setDataRevogacao(c.getDataRevogacao());
        dto.setStatus(c.getStatus().toString());
        dto.setHashBlockchain(c.getHashBlockchain());
        dto.setEnderecoTransacao(c.getEnderecoTransacao());
        return dto;
    }
}
