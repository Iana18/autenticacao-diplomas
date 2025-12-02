package com.meuprojetotcc.autenticacao_diplomas.service;

import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.model.validacao.Validacao;
import com.meuprojetotcc.autenticacao_diplomas.repository.DiplomaRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.UserRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.ValidacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ValidacaoService {

    private final DiplomaRepository diplomaRepository;
    private final UserRepository userRepository;
    private final ValidacaoRepository validacaoRepository;

    public ValidacaoService(DiplomaRepository diplomaRepository,
                            UserRepository userRepository,
                            ValidacaoRepository validacaoRepository) {
        this.diplomaRepository = diplomaRepository;
        this.userRepository = userRepository;
        this.validacaoRepository = validacaoRepository;
    }

    public Diploma validar(String numeroDiploma, String hashBlockchain, String emailVerificador) {

        // 1️⃣ Busca diploma pelo número e hash
        Diploma diploma = diplomaRepository
                .findByNumeroDiplomaAndHashBlockchain(numeroDiploma, hashBlockchain)
                .orElseThrow(() -> new RuntimeException("Diploma inválido ou não encontrado"));

        // 2️⃣ Busca usuário que está verificando
        User verificador = userRepository
                .findByEmail(emailVerificador)
                .orElseThrow(() -> new RuntimeException("Usuário verificador não encontrado"));

        // 3️⃣ Registra a validação
        Validacao validacao = new Validacao();
        validacao.setDiploma(diploma);
        validacao.setVerificador(verificador);
        validacao.setDataVerificacao(LocalDateTime.now());

        validacaoRepository.save(validacao);

        return diploma;
    }
}
