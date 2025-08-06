package com.meuprojetotcc.autenticacao_diplomas.controller;

import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Certificado;
import com.meuprojetotcc.autenticacao_diplomas.model.certificado.CertificadoDTO;
import com.meuprojetotcc.autenticacao_diplomas.service.CertificadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificados")
public class CertificadoController {

    private final CertificadoService service;

    public CertificadoController(CertificadoService service) {
        this.service = service;
    }

    @PostMapping("/registrar")
    public ResponseEntity<Certificado> registrar(@RequestBody CertificadoDTO dto) {
        Certificado criado = service.registrarCertificado(dto);
        return ResponseEntity.ok(criado);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Certificado> atualizar(@PathVariable Long id, @RequestBody CertificadoDTO dto) {
        try {
            Certificado atualizado = service.atualizar(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/revogar/{id}")
    public ResponseEntity<Certificado> revogar(@PathVariable Long id) {
        try {
            Certificado revogado = service.revogar(id);
            return ResponseEntity.ok(revogado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/emitir/{id}")
    public ResponseEntity<Certificado> emitir(@PathVariable Long id) {
        try {
            Certificado emitido = service.reemitir(id);
            return ResponseEntity.ok(emitido);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Certificado>> listarTodos() {
        List<Certificado> certificados = service.verTodos();
        return ResponseEntity.ok(certificados);
    }

    @GetMapping("/estudante/{id}")
    public ResponseEntity<List<Certificado>> porEstudante(@PathVariable Long id) {
        List<Certificado> certificados = service.verPorEstudante(id);
        if (certificados == null || certificados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(certificados);
    }
}
