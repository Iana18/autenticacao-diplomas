package com.meuprojetotcc.autenticacao_diplomas.controller;

import com.meuprojetotcc.autenticacao_diplomas.model.Instituicao.Instituicao;
import com.meuprojetotcc.autenticacao_diplomas.model.Instituicao.InstituicaoDto;
import com.meuprojetotcc.autenticacao_diplomas.service.InstituicaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instituicoes")
public class InstituicaoController {

    private final InstituicaoService instituicaoService;

    public InstituicaoController(InstituicaoService instituicaoService) {
        this.instituicaoService = instituicaoService;
    }

    @PostMapping("/criar")
    public ResponseEntity<Instituicao> criarInstituicao(@RequestBody InstituicaoDto instituicaoDto) {
        Instituicao instituicao = instituicaoService.criarInstituicao(instituicaoDto);
        return ResponseEntity.ok(instituicao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instituicao> buscarPorId(@PathVariable Long id) {
        return instituicaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Instituicao>> listarPorNome(@RequestParam(required = false) String nome) {
        List<Instituicao> instituicoes;
        if (nome != null && !nome.isEmpty()) {
            instituicoes = instituicaoService.buscarPorNome(nome);
        } else {
            instituicoes = instituicaoService.listarTodas();
        }
        return ResponseEntity.ok(instituicoes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instituicao> atualizar(@PathVariable Long id, @RequestBody InstituicaoDto instituicaoDto) {
        try {
            Instituicao atualizada = instituicaoService.atualizarInstituicao(id, instituicaoDto);
            return ResponseEntity.ok(atualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            instituicaoService.deletarInstituicao(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
