package com.meuprojetotcc.autenticacao_diplomas.controller;


import com.meuprojetotcc.autenticacao_diplomas.model.certificado.*;
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

    @GetMapping
    public ResponseEntity<List<Certificado>> listarTodos() {
        return ResponseEntity.ok(service.verTodos());
    }

    @GetMapping("/estudante/{id}")
    public ResponseEntity<List<Certificado>> porEstudante(@PathVariable Long id) {
        List<Certificado> certificados = service.verPorEstudante(id);
        if (certificados == null || certificados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(certificados);
    }

    @PutMapping("/revogar/{id}")
    public ResponseEntity<Certificado> revogar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.revogar(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/emitir/{id}")
    public ResponseEntity<Certificado> emitir(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.reemitir(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
