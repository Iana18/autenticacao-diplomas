package com.meuprojetotcc.autenticacao_diplomas.service;

import com.meuprojetotcc.autenticacao_diplomas.model.Curso.Curso;
import com.meuprojetotcc.autenticacao_diplomas.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public Curso salvar(Curso curso) {
        return cursoRepository.save(curso);
    }

    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    public Optional<Curso> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public void deletar(Long id) {
        cursoRepository.deleteById(id);
    }

    // Atualizar curso
    public Curso atualizar(String nome, Curso cursoAtualizado) {
        Optional<Curso> cursoExistente = cursoRepository.findByNome(nome);
        if (cursoExistente.isPresent()) {
            Curso curso = cursoExistente.get();
            curso.setNome(cursoAtualizado.getNome());
            curso.setDescricao(cursoAtualizado.getDescricao());
            return cursoRepository.save(curso);
        } else {
            throw new RuntimeException("Curso não encontrado com id " + nome);
        }
    }
}
