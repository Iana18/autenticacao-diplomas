package com.meuprojetotcc.autenticacao_diplomas.controller;

import com.meuprojetotcc.autenticacao_diplomas.service.DiplomaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ministerio")
@CrossOrigin(origins = "*")
public class MinisterioController {

    private final DiplomaService diplomaService;

    public MinisterioController(DiplomaService diplomaService) {
        this.diplomaService = diplomaService;
    }

    // Apenas ministério pode consultar qualquer diploma
    @GetMapping("/diplomas/{numeroMatricula}")
    @PreAuthorize("hasRole('MINISTERIO')")
    public ResponseEntity<?> consultarDiploma(@PathVariable String numeroMatricula) {
        var diplomas = diplomaService.buscarPorNumeroMatricula(numeroMatricula);
        if (diplomas.isEmpty())
            return ResponseEntity.status(404).body("Diploma não encontrado");
        return ResponseEntity.ok(diplomas);
    }
}
