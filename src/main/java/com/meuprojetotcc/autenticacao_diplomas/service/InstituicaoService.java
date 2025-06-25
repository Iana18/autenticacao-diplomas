package com.meuprojetotcc.autenticacao_diplomas.service;

import com.meuprojetotcc.autenticacao_diplomas.model.Instituicao.Instituicao;
import com.meuprojetotcc.autenticacao_diplomas.repository.InstituicaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstituicaoService {

    private final InstituicaoRepository instituicaoRepository;

    public InstituicaoService(InstituicaoRepository repo) {
        this.instituicaoRepository = repo;
    }

    public Instituicao salvar(Instituicao instituicao) {
        return instituicaoRepository.save(instituicao);
    }

    public List<Instituicao> listarTodos() {
        return instituicaoRepository.findAll();
    }

    public Optional<Instituicao> buscarPorId(Long id) {
        return instituicaoRepository.findById(id);
    }

    public void deletar(Long id) {
        instituicaoRepository.deleteById(id);
    }
}
