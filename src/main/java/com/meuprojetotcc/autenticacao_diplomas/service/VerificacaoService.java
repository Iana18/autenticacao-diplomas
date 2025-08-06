package com.meuprojetotcc.autenticacao_diplomas.service;

import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Certificado;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.model.verificacao.Verificacao;
import com.meuprojetotcc.autenticacao_diplomas.repository.VerificacaoRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.CertificadoRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VerificacaoService {

    private final VerificacaoRepository verificacaoRepository;
    private final CertificadoRepository certificadoRepository;
    private final UserRepository userRepository;

    public VerificacaoService(VerificacaoRepository verificacaoRepository,
                              CertificadoRepository certificadoRepository,
                              UserRepository userRepository) {
        this.verificacaoRepository = verificacaoRepository;
        this.certificadoRepository = certificadoRepository;
        this.userRepository = userRepository;
    }

    public List<Verificacao> listarTodas() {
        return verificacaoRepository.findAll();
    }

    public Optional<Verificacao> buscarPorId(Long id) {
        return verificacaoRepository.findById(id);
    }

    public Verificacao criarVerificacao(Long certificadoId, Long verificadorId) {
        Certificado certificado = certificadoRepository.findById(certificadoId)
                .orElseThrow(() -> new RuntimeException("Certificado não encontrado"));
        User verificador = userRepository.findById(verificadorId)
                .orElseThrow(() -> new RuntimeException("Usuário verificador não encontrado"));

        Verificacao verificacao = new Verificacao();
        verificacao.setCertificado(certificado);
        verificacao.setVerificador(verificador);
        verificacao.setDataVerificacao(LocalDateTime.now());

        return verificacaoRepository.save(verificacao);
    }

    public Verificacao atualizarVerificacao(Long id, Long certificadoId, Long verificadorId, LocalDateTime novaData) {
        Verificacao verificacao = verificacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Verificação não encontrada"));

        if (certificadoId != null) {
            Certificado certificado = certificadoRepository.findById(certificadoId)
                    .orElseThrow(() -> new RuntimeException("Certificado não encontrado"));
            verificacao.setCertificado(certificado);
        }

        if (verificadorId != null) {
            User verificador = userRepository.findById(verificadorId)
                    .orElseThrow(() -> new RuntimeException("Usuário verificador não encontrado"));
            verificacao.setVerificador(verificador);
        }

        if (novaData != null) {
            verificacao.setDataVerificacao(novaData);
        }

        return verificacaoRepository.save(verificacao);
    }

    public void deletarVerificacao(Long id) {
        verificacaoRepository.deleteById(id);
    }
}
