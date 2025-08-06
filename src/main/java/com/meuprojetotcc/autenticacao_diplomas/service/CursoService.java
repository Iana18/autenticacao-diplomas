package com.meuprojetotcc.autenticacao_diplomas.service;

import com.meuprojetotcc.autenticacao_diplomas.model.Curso.Curso;

import com.meuprojetotcc.autenticacao_diplomas.model.Curso.CursoDto;
import com.meuprojetotcc.autenticacao_diplomas.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {
    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public Curso criarCurso(CursoDto dto) {
        if (cursoRepository.existsByNome(dto.getNome())) {
            throw new RuntimeException("Curso com esse nome já existe");
        }
        Curso curso = new Curso();
        curso.setNome(dto.getNome());
        curso.setDescricao(dto.getDescricao());
        return cursoRepository.save(curso);
    }

    public Optional<Curso> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public Optional<Curso> buscarPorNome(String nome) {
        return cursoRepository.findByNome(nome);
    }

    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    public Curso atualizarCurso(Long id, CursoDto dto) {
        Curso curso = cursoRepository.findById(id).orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        if (!curso.getNome().equals(dto.getNome()) && cursoRepository.existsByNome(dto.getNome())) {
            throw new RuntimeException("Curso com esse nome já existe");
        }

        curso.setNome(dto.getNome());
        curso.setDescricao(dto.getDescricao());

        return cursoRepository.save(curso);
    }

    public void deletarCurso(Long id) {
        cursoRepository.deleteById(id);
    }
}
