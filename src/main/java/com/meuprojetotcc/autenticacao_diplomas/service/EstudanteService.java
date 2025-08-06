package com.meuprojetotcc.autenticacao_diplomas.service;

import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.repository.EstudanteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstudanteService {

    private final EstudanteRepository estudanteRepository;

    private final PasswordEncoder passwordEncoder;

    public EstudanteService(EstudanteRepository estudanteRepository, PasswordEncoder passwordEncoder) {
        this.estudanteRepository = estudanteRepository;
        this.passwordEncoder = passwordEncoder;
    }


    //public EstudanteService(EstudanteRepository estudanteRepository) {
      //  this.estudanteRepository = estudanteRepository;
   // }

    public Estudante salvarEstudante(Estudante estudante) {
        if(estudanteRepository.existsByEmail(estudante.getEmail())) {
            throw new RuntimeException("Email já cadastrado.");
        }
        if(estudanteRepository.existsByNumeroMatricula(estudante.getNumeroMatricula())) {
            throw new RuntimeException("Número de matrícula já cadastrado.");
        }
        // Criptografa senha antes de salvar
        estudante.setSenha(passwordEncoder.encode(estudante.getSenha()));
        return estudanteRepository.save(estudante);
    }

    public List<Estudante> buscarPorNome(String nome) {
        return estudanteRepository.findByNomeCompletoContainingIgnoreCase(nome);
    }

    public boolean existsByNumeroMatricula(String numeroMatricula) {
        return estudanteRepository.existsByNumeroMatricula(numeroMatricula);
    }

    public boolean existsByEmail(String email) {
        return estudanteRepository.existsByEmail(email);
    }
    public Optional<Estudante> buscarPorId(Long id) {
        return estudanteRepository.findById(id);
    }

    public void deletarEstudante(Long id) {
        estudanteRepository.deleteById(id);
    }

    public Estudante atualizarEstudante(Long id, Estudante dadosAtualizados) {
        return estudanteRepository.findById(id).map(estudante -> {
            estudante.setNomeCompleto(dadosAtualizados.getNomeCompleto());
            estudante.setEmail(dadosAtualizados.getEmail());
            estudante.setNumeroMatricula(dadosAtualizados.getNumeroMatricula());
            estudante.setDataNascimento(dadosAtualizados.getDataNascimento());
            estudante.setGenero(dadosAtualizados.getGenero());

            // Atualiza senha somente se foi passada nova senha diferente
            if (dadosAtualizados.getSenha() != null && !dadosAtualizados.getSenha().isEmpty()) {
                estudante.setSenha(passwordEncoder.encode(dadosAtualizados.getSenha()));
            }

            return estudanteRepository.save(estudante);
        }).orElseThrow(() -> new RuntimeException("Estudante não encontrado"));
    }


    public List<Estudante> listarTodos() {
        return estudanteRepository.findAll();
    }
}
