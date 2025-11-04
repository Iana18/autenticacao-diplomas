package com.meuprojetotcc.autenticacao_diplomas.service;

import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.AlterarSenhaDTO;
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

    // ================= Cadastro pelo estudante público =================
    public Estudante registrarEstudante(String nomeCompleto, String numeroMatricula, String senha) {
        // Busca o estudante já cadastrado pelo emissor
        Estudante estudanteExistente = estudanteRepository
                .findByNomeCompletoAndNumeroMatricula(nomeCompleto, numeroMatricula)
                .orElseThrow(() -> new RuntimeException("Dados não conferem com cadastro do emissor"));

        if (estudanteExistente.getSenha() != null && !estudanteExistente.getSenha().isEmpty()) {
            throw new RuntimeException("Estudante já possui conta cadastrada");
        }

        // Define senha criptografada
        estudanteExistente.setSenha(passwordEncoder.encode(senha));

        // Caso queira marcar que foi criado pelo estudante
        // estudanteExistente.setCriadoPor(false); // se tiver esse campo boolean

        return estudanteRepository.save(estudanteExistente);
    }

    // ================= Cadastro pelo emissor =================
    public Estudante cadastrarEstudantePeloEmissor(Estudante estudante) {
        // Apenas salva o estudante, sem senha
        estudante.setSenha(null); // sem senha
        return estudanteRepository.save(estudante);
    }

    // ================= Buscar estudantes =================
    public List<Estudante> buscarPorNome(String nome) {
        return estudanteRepository.findByNomeCompletoContainingIgnoreCase(nome);
    }

    public Optional<Estudante> buscarPorId(Long id) {
        return estudanteRepository.findById(id);
    }

    public boolean existsByNumeroMatricula(String numeroMatricula) {
        return estudanteRepository.existsByNumeroMatricula(numeroMatricula);
    }

    public boolean existsByEmail(String email) {
        return estudanteRepository.existsByEmail(email);
    }

    public List<Estudante> listarTodos() {
        return estudanteRepository.findAll();
    }

    // ================= Atualizar dados do estudante =================
    public Estudante atualizarEstudante(Long id, Estudante dadosAtualizados) {
        return estudanteRepository.findById(id).map(estudante -> {
            estudante.setNomeCompleto(dadosAtualizados.getNomeCompleto());
            estudante.setEmail(dadosAtualizados.getEmail());
            estudante.setNumeroMatricula(dadosAtualizados.getNumeroMatricula());
            estudante.setDataNascimento(dadosAtualizados.getDataNascimento());
            estudante.setGenero(dadosAtualizados.getGenero());

            // Atualiza senha somente se foi passada nova senha
            if (dadosAtualizados.getSenha() != null && !dadosAtualizados.getSenha().isEmpty()) {
                estudante.setSenha(passwordEncoder.encode(dadosAtualizados.getSenha()));
            }

            return estudanteRepository.save(estudante);
        }).orElseThrow(() -> new RuntimeException("Estudante não encontrado"));
    }

    // ================= Deletar =================
    public void deletarEstudante(Long id) {
        estudanteRepository.deleteById(id);
    }

    // ================= Alterar senha =================
    public void alterarSenha(Long estudanteId, AlterarSenhaDTO dto) {
        Estudante estudante = estudanteRepository.findById(estudanteId)
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado"));

        // Verifica senha atual
        if (!passwordEncoder.matches(dto.getSenhaAtual(), estudante.getSenha())) {
            throw new RuntimeException("Senha atual incorreta");
        }

        // Atualiza com a nova senha
        estudante.setSenha(passwordEncoder.encode(dto.getSenhaNova()));
        estudanteRepository.save(estudante);
    }


   /* public Optional<Estudante> buscarPorNumeroMatricula(String numeroMatricula) {
        return estudanteRepository.findByNumeroMatricula(numeroMatricula);
    }
*/

    // EstudanteService.java
    public Estudante buscarPorNumeroMatricula(String numeroMatricula) {
        return estudanteRepository.findByNumeroMatricula(numeroMatricula)
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado com a matrícula: " + numeroMatricula));
    }

}
