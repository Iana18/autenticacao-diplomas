package com.meuprojetotcc.autenticacao_diplomas.controller;

import com.meuprojetotcc.autenticacao_diplomas.model.verificacao.VerificacaoDto;
import com.meuprojetotcc.autenticacao_diplomas.model.verificacao.Verificacao;
import com.meuprojetotcc.autenticacao_diplomas.model.verificacao.VerificacaoDto;
import com.meuprojetotcc.autenticacao_diplomas.service.VerficacaoService;
import com.meuprojetotcc.autenticacao_diplomas.service.VerficacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/verificacoes")
public class VerificacaoController {

    private final VerficacaoService verficacaoService;

    public VerificacaoController(VerficacaoService verficacaoService) {
        this.verficacaoService = verficacaoService;
    }

    @PostMapping
    public ResponseEntity<Verificacao> criar(@RequestBody VerificacaoDto dto) {
        Verificacao v = verficacaoService.registrarVerificacao(dto);
        return ResponseEntity.ok(v);
    }

    @GetMapping("/certificado/{certificadoId}")
    public ResponseEntity<List<Verificacao>> buscarPorCertificado(@PathVariable Long certificadoId) {
        List<Verificacao> lista = verficacaoService.buscarPorCertificado(certificadoId);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }
}
