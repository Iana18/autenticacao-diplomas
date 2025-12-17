package com.meuprojetotcc.autenticacao_diplomas.service;

import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Status;
import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.model.validacao.Validacao;
import com.meuprojetotcc.autenticacao_diplomas.repository.DiplomaRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.UserRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.ValidacaoRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Arrays;

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

    /**
     * Valida o diploma verificando hash na blockchain e registra a verificação.
     */
    public Diploma validarDiploma(String numeroDiploma, String emailVerificador) throws Exception {

        // 1️⃣ Diploma existe?
        Diploma diploma = diplomaRepository.findByNumeroDiploma(numeroDiploma)
                .orElseThrow(() -> new RuntimeException("Diploma não encontrado"));

        // 2️⃣ Diploma está ATIVO?
        if (diploma.getStatus() != Status.ATIVO) {
            throw new RuntimeException("Diploma não está ativo");
        }

        // 3️⃣ Verifica hash do diploma
        String dadosDiploma = diploma.getEstudante().getNomeCompleto()
                + diploma.getCurso().getNome()
                + diploma.getInstituicao().getNome()
                + diploma.getGrauAcademico()
                + diploma.getDataEmissao()
                + diploma.getNumeroDiploma();

        if (!validarHash(dadosDiploma, diploma.getHashBlockchain())) {
            throw new RuntimeException("Hash do diploma não confere com a blockchain");
        }

        // 4️⃣ Usuário verificador existe?
        User verificador = userRepository.findByEmail(emailVerificador)
                .orElseThrow(() -> new RuntimeException("Usuário verificador não encontrado"));

        // 5️⃣ Salvar histórico de validação
        Validacao validacao = new Validacao();
        validacao.setDiploma(diploma);
        validacao.setVerificador(verificador);
        validacao.setDataVerificacao(LocalDateTime.now());
        validacaoRepository.save(validacao);

        return diploma;
    }

    /**
     * Compara hash local com hash registrado na blockchain
     */
    private boolean validarHash(String dadosDiploma, String hashHexBlockchain) throws Exception {
        byte[] hashBlockchain = hexStringToByteArray(hashHexBlockchain.replace("0x", ""));
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashLocal = digest.digest(dadosDiploma.getBytes(StandardCharsets.UTF_8));
        return Arrays.equals(hashBlockchain, hashLocal);
    }

    /**
     * Converte string hex para bytes
     */
    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
