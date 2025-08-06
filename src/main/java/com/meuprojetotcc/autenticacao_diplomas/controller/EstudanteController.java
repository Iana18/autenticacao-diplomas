package com.meuprojetotcc.autenticacao_diplomas.controller;

import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.repository.EstudanteRepository;
import com.meuprojetotcc.autenticacao_diplomas.service.EstudanteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudantes")
public class EstudanteController {

    private final EstudanteService estudanteService;
    private final EstudanteRepository estudanteRepository;


    private final PasswordEncoder passwordEncoder;



    public EstudanteController(EstudanteService estudanteService , EstudanteRepository estudanteRepository,
                               PasswordEncoder passwordEncoder) {
        this.estudanteService = estudanteService;
        this.estudanteRepository = estudanteRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping
    public ResponseEntity<?> criarEstudante(@RequestBody Estudante estudante) {
        if (estudanteService.existsByNumeroMatricula(estudante.getNumeroMatricula())) {
            return ResponseEntity.badRequest().body("Número de matrícula já cadastrado");
        }
        if (estudanteService.existsByEmail(estudante.getEmail())) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }

        // Aqui NÃO precisa criptografar, o service já faz isso
        Estudante criado = estudanteService.salvarEstudante(estudante);
        return ResponseEntity.ok(criado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudante> buscarPorId(@PathVariable Long id) {
        return estudanteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Estudante>> buscarPorNome(@RequestParam String nome) {
        List<Estudante> estudantes = estudanteService.buscarPorNome(nome);
        return ResponseEntity.ok(estudantes);
    }

    @GetMapping
    public ResponseEntity<List<Estudante>> listarTodos() {
        List<Estudante> estudantes = estudanteService.listarTodos();
        return ResponseEntity.ok(estudantes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudante> atualizarEstudante(@PathVariable Long id, @RequestBody Estudante estudante) {
        try {
            Estudante atualizado = estudanteService.atualizarEstudante(id, estudante);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEstudante(@PathVariable Long id) {
        estudanteService.deletarEstudante(id);
        return ResponseEntity.noContent().build();
    }
}
