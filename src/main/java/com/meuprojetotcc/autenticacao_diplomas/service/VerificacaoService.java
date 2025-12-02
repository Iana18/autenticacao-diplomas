package com.meuprojetotcc.autenticacao_diplomas.service;

import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.model.verificacao.Verificacao;
import com.meuprojetotcc.autenticacao_diplomas.repository.DiplomaRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.UserRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.VerificacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VerificacaoService {

    private final VerificacaoRepository verificacaoRepository;
    private final DiplomaRepository diplomaRepository;
    private final UserRepository userRepository;

    public VerificacaoService(VerificacaoRepository verificacaoRepository,
                              DiplomaRepository diplomaRepository,
                              UserRepository userRepository) {
        this.verificacaoRepository = verificacaoRepository;
        this.diplomaRepository = diplomaRepository;
        this.userRepository = userRepository;
    }

    public List<Verificacao> listarTodas() {
        return verificacaoRepository.findAll();
    }


    public User buscarUsuarioPorEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
    public Verificacao criarVerificacao(Long diplomaId, Long verificadorId) {
        Diploma diploma = diplomaRepository.findById(diplomaId)
                .orElseThrow(() -> new RuntimeException("Diploma não encontrado"));

        User verificador = userRepository.findById(verificadorId)
                .orElseThrow(() -> new RuntimeException("Usuário verificador não encontrado"));

        Verificacao verificacao = new Verificacao();
        verificacao.setDiploma(diploma);
        verificacao.setVerificador(verificador);
        verificacao.setDataVerificacao(LocalDateTime.now());

        return verificacaoRepository.save(verificacao);
    }
}
