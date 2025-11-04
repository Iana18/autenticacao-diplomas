package com.meuprojetotcc.autenticacao_diplomas.service;

import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.model.verificacao.Verificacao;
import com.meuprojetotcc.autenticacao_diplomas.repository.DiplomaRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.UserRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.VerificacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ValidacaoService {

    private final DiplomaRepository diplomaRepository;
    private final VerificacaoRepository verificacaoRepository;
    private final UserRepository userRepository;

    public ValidacaoService(DiplomaRepository diplomaRepository,
                            VerificacaoRepository verificacaoRepository,
                            UserRepository userRepository) {
        this.diplomaRepository = diplomaRepository;
        this.verificacaoRepository = verificacaoRepository;
        this.userRepository = userRepository;
    }

    public Diploma validarDiploma(String hashBlockchain, String numeroMatricula, Long verificadorId) {
        // Procura diploma pelo hash + matrícula
        Optional<Diploma> diplomaOpt = diplomaRepository
                .findByHashBlockchainAndEstudante_NumeroMatricula(hashBlockchain, numeroMatricula);

        if (diplomaOpt.isEmpty()) {
            throw new RuntimeException("Diploma inválido ou não encontrado.");
        }

        Diploma diploma = diplomaOpt.get();

        // Registra a verificação
        User verificador = userRepository.findById(verificadorId)
                .orElseThrow(() -> new RuntimeException("Usuário verificador não encontrado"));

        Verificacao verificacao = new Verificacao();
        verificacao.setDiploma(diploma);
        verificacao.setVerificador(verificador);
        verificacao.setDataVerificacao(LocalDateTime.now());
        verificacaoRepository.save(verificacao);

        return diploma;
    }
}
