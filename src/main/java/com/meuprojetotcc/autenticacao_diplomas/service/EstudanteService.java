package com.meuprojetotcc.autenticacao_diplomas.service;

import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
//import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.repository.EstudanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstudanteService {


    @Autowired
    private EstudanteRepository estudanteRepository;

   /* public EstudanteService(EstudanteRepository repo) {
        this.repo = repo;
    }*/

    public Estudante criarEstudante(Estudante estudante) {
        if (estudanteRepository.existsByEmail(estudante.getEmail())) {
            throw new RuntimeException("Já existe um estudante com este e-mail.");
        }
        if (estudanteRepository.existsByNumeroMatricula(estudante.getNumeroMatricula())) {
            throw new RuntimeException("Já existe um estudante com este número de matrícula.");
        }
        return estudanteRepository.save(estudante);
    }

    public List<Estudante> listarTodos() {
        return estudanteRepository.findAll();
    }

    public Optional<Estudante> buscarPorId(Long id) {
        return estudanteRepository.findById(id);
    }

    public Estudante atualizarEstudante(Long id, Estudante novosDados) {
        return estudanteRepository.findById(id).map(estudante -> {
            estudante.setNomeCompleto(novosDados.getNomeCompleto());
            estudante.setEmail(novosDados.getEmail());
            estudante.setNumeroMatricula(novosDados.getNumeroMatricula());
            estudante.setDataNascimento(novosDados.getDataNascimento());
            estudante.setGenero(novosDados.getGenero());
            return estudanteRepository.save(estudante);
        }).orElseThrow(() -> new RuntimeException("Estudante não encontrado com ID: " + id));
    }

    public void removerEstudante(Long id) {
        if (!estudanteRepository.existsById(id)) {
            throw new RuntimeException("Estudante não encontrado com ID: " + id);
        }
        estudanteRepository.deleteById(id);
    }

    public List<Estudante> buscarPorNomeCompleto(String nomeCompleto) {
        return estudanteRepository.findByNomeCompleto(nomeCompleto);
    }

    public List<Estudante> buscarPorNomeCompletoContainingIgnoreCase(String nomeCompleto) {
        return estudanteRepository.findByNomeCompletoContainingIgnoreCase(nomeCompleto);
    }
}
