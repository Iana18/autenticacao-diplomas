package com.meuprojetotcc.autenticacao_diplomas.repository;

import com.meuprojetotcc.autenticacao_diplomas.model.Curso.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CursoRepository  extends JpaRepository<Curso, Long> {
    // Buscar curso por nome exato
    Optional<Curso> findByNome(String nome);
    boolean existsByNome(String nome);

    // Buscar cursos que contenham parte do nome, ignorando maiúsculas/minúsculas
    List<Curso> findByNomeContainingIgnoreCase(String nome);
    Optional<Curso> findById(Long id);
}
