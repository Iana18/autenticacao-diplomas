package com.meuprojetotcc.autenticacao_diplomas.service;

import com.meuprojetotcc.autenticacao_diplomas.model.Instituicao.Instituicao;
import com.meuprojetotcc.autenticacao_diplomas.model.Instituicao.InstituicaoDto;
import com.meuprojetotcc.autenticacao_diplomas.repository.InstituicaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstituicaoService {

    private final InstituicaoRepository instituicaoRepository;

    public InstituicaoService(InstituicaoRepository instituicaoRepository) {
        this.instituicaoRepository = instituicaoRepository;
    }

    public Instituicao criarInstituicao(InstituicaoDto instituicaoDto) {
        if (instituicaoRepository.existsByNome(instituicaoDto.getNome())) {
            throw new RuntimeException("Instituição já cadastrada com esse nome.");
        }

        Instituicao instituicao = new Instituicao();
        instituicao.setNome(instituicaoDto.getNome());

        return instituicaoRepository.save(instituicao);
    }

    public Optional<Instituicao> buscarPorId(Long id) {
        return instituicaoRepository.findById(id);
    }

    public List<Instituicao> buscarPorNome(String nome) {
        return instituicaoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Instituicao> listarTodas() {
        return instituicaoRepository.findAll();
    }

    public Instituicao atualizarInstituicao(Long id, InstituicaoDto instituicaoDto) {
        Optional<Instituicao> optional = instituicaoRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Instituição não encontrada");
        }

        Instituicao instituicao = optional.get();
        instituicao.setNome(instituicaoDto.getNome());
        return instituicaoRepository.save(instituicao);
    }

    public void deletarInstituicao(Long id) {
        if (!instituicaoRepository.existsById(id)) {
            throw new RuntimeException("Instituição não encontrada");
        }
        instituicaoRepository.deleteById(id);
    }
}
