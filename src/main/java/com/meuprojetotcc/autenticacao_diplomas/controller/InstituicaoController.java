package com.meuprojetotcc.autenticacao_diplomas.controller;

import com.meuprojetotcc.autenticacao_diplomas.model.Instituicao.Instituicao;
import com.meuprojetotcc.autenticacao_diplomas.service.InstituicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instituicoes")
public class InstituicaoController {

    @Autowired
    private InstituicaoService instituicaoService;

   // public InstituicaoController(InstituicaoService service) {
    //    this.service = service;
  //  }

    @PostMapping
    public ResponseEntity<Instituicao> criar(@RequestBody Instituicao instituicao) {
        Instituicao salvo = instituicaoService.salvar(instituicao);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instituicao> buscarPorId(@PathVariable Long id) {
        return instituicaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Instituicao> listarTodos() {
        return instituicaoService.listarTodos();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        instituicaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
