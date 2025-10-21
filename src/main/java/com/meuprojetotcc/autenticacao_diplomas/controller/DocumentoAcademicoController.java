package com.meuprojetotcc.autenticacao_diplomas.controller;

import com.meuprojetotcc.autenticacao_diplomas.model.DocumentoAcademico;
import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Certificado;
import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import com.meuprojetotcc.autenticacao_diplomas.service.DocumentoAcademicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documentos")
@CrossOrigin(origins = "*")
public class DocumentoAcademicoController {

    private final DocumentoAcademicoService documentoService;
    private  final  DocumentoAcademicoService documentoAcademicoService;

    public DocumentoAcademicoController(DocumentoAcademicoService documentoService,
                                        DocumentoAcademicoService documentoAcademicoService) {
        this.documentoService = documentoService;
        this.documentoAcademicoService = documentoAcademicoService;
    }



    // =================== Buscar todos documentos de um estudante ===================
    @GetMapping("/estudante/{id}")
    public ResponseEntity<?> getDocumentosPorEstudante(@PathVariable Long id) {
        try {
            List<DocumentoAcademico> documentos = documentoService.buscarPorEstudante(id);
            if (documentos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Nenhum documento encontrado para este estudante.");
            }
            return ResponseEntity.ok(documentos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar documentos: " + e.getMessage());
        }
    }

    // =================== Buscar por hash (blockchain) ===================
    @GetMapping("/hash/{hash}")
    public ResponseEntity<?> getPorHash(@PathVariable String hash) {
        try {
            Optional<DocumentoAcademico> doc = documentoService.buscarPorHash(hash);
            return doc.<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Documento não encontrado com o hash informado."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar documento por hash: " + e.getMessage());
        }
    }

    // =================== Revogar documento ===================
    @PutMapping("/{tipo}/{id}/revogar")
    public ResponseEntity<?> revogarDocumento(@PathVariable String tipo, @PathVariable Long id) {
        try {
            Optional<DocumentoAcademico> doc = documentoService.buscarPorId(
                    id,
                    tipo.equalsIgnoreCase("certificado") ? Certificado.class : Diploma.class
            );

            if (doc.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento não encontrado.");
            }

            DocumentoAcademico atualizado = documentoService.revogarDocumento(doc.get());
            return ResponseEntity.ok(atualizado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao revogar documento: " + e.getMessage());
        }
    }

    // =================== Reemitir documento ===================
    @PutMapping("/{tipo}/{id}/reemitir")
    public ResponseEntity<?> reemitirDocumento(@PathVariable String tipo, @PathVariable Long id) {
        try {
            Optional<DocumentoAcademico> doc = documentoService.buscarPorId(
                    id,
                    tipo.equalsIgnoreCase("certificado") ? Certificado.class : Diploma.class
            );

            if (doc.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento não encontrado.");
            }

            DocumentoAcademico atualizado = documentoService.reemitirDocumento(doc.get());
            return ResponseEntity.ok(atualizado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao reemitir documento: " + e.getMessage());
        }
    }

    // =================== Atualizar hash/blockchain ===================
    @PutMapping("/{tipo}/{id}/blockchain")
    public ResponseEntity<?> atualizarBlockchain(
            @PathVariable String tipo,
            @PathVariable Long id,
            @RequestParam String novoHash,
            @RequestParam String enderecoTx) {
        try {
            Optional<DocumentoAcademico> doc = documentoService.buscarPorId(
                    id,
                    tipo.equalsIgnoreCase("certificado") ? Certificado.class : Diploma.class
            );

            if (doc.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento não encontrado.");
            }

            DocumentoAcademico atualizado = documentoService.atualizarBlockchain(doc.get(), novoHash, enderecoTx);
            return ResponseEntity.ok(atualizado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar blockchain do documento: " + e.getMessage());
        }
    }

    @GetMapping("/buscar-por-nome")
    public ResponseEntity<?> buscarPorNome(@RequestParam String nome) {
        try {
            List<DocumentoAcademico> documentos = documentoAcademicoService.buscarPorNomeEstudante(nome);
            if (documentos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Nenhum documento encontrado para o estudante com nome: " + nome);
            }
            return ResponseEntity.ok(documentos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar documentos: " + e.getMessage());
        }
    }

}
